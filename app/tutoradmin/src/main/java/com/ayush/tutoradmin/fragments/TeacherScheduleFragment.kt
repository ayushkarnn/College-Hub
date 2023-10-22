package com.ayush.tutoradmin.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayush.tutoradmin.R
import com.ayush.tutoradmin.adapter.TeacherScheduleAdapter
import com.ayush.tutoradmin.datamodel.Schedule
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class TeacherScheduleFragment : Fragment() {

    private lateinit var myRv: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var edit:EditText
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private val scheduleList = mutableListOf<Schedule>()
    private lateinit var scheduleAdapter: TeacherScheduleAdapter
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null


    private lateinit var scheduleImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_teacher_schedule, container, false)

        myRv = view.findViewById(R.id.myRv)
        fab = view.findViewById(R.id.fab)
        edit = view.findViewById(R.id.edit)
        edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterScheduleList(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        databaseReference = FirebaseDatabase.getInstance().reference.child("TeacherSchedule")
        storageReference = FirebaseStorage.getInstance().reference.child("teacher_images")

        val layoutManager = LinearLayoutManager(requireContext())
        myRv.layoutManager = layoutManager
        scheduleAdapter = TeacherScheduleAdapter(scheduleList)
        myRv.adapter = scheduleAdapter

        fab.setOnClickListener {
            openDialog()
        }

        updateScheduleList()

        return view
    }



    private fun filterScheduleList(query: String) {
        val filteredList = mutableListOf<Schedule>()
        CoroutineScope(Dispatchers.Default).launch {
            for (schedule in scheduleList) {
                if (schedule.teacherName.contains(query, ignoreCase = true) ||
                    schedule.teacherEcode.contains(query, ignoreCase = true)) {
                    filteredList.add(schedule)
                }
            }
            withContext(Dispatchers.Main) {
                scheduleAdapter = TeacherScheduleAdapter(filteredList)
                myRv.adapter = scheduleAdapter
            }
        }
    }


    private fun openDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.scheduledialog, null)

        val edtTeacherName = dialogView.findViewById<EditText>(R.id.edtTeacherName)
        val edtTeacherEcode = dialogView.findViewById<EditText>(R.id.edtTeacherEcode)
        val edtTeacherRole = dialogView.findViewById<EditText>(R.id.edtTeacherRole)
        val edtTeacherSittingRoom = dialogView.findViewById<EditText>(R.id.edtTeacherSittingRoom)
        val edtTeacherEmail = dialogView.findViewById<EditText>(R.id.edtTeacherEmail)
        val edtTeacherPhone = dialogView.findViewById<EditText>(R.id.edtTeacherPhone)


        scheduleImageView = dialogView.findViewById(R.id.scheduleImageView)
        val btnSubmit = dialogView.findViewById<Button>(R.id.btnSubmit)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()

        scheduleImageView.setOnClickListener {
            openGallery()
        }

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


        btnSubmit.setOnClickListener {
            val teacherName = edtTeacherName.text.toString()
            val teacherEcode = edtTeacherEcode.text.toString()
            val teacherRole =  edtTeacherRole.text.toString()
            val teacherRoom = edtTeacherSittingRoom.text.toString()
            val teacherEmail = edtTeacherEmail.text.toString()
            val teacherPhone = edtTeacherPhone.text.toString()

            if (teacherName.isNotEmpty() && teacherEcode.isNotEmpty() && teacherRole.isNotEmpty() && teacherRoom.isNotEmpty() &&   teacherEmail.isNotEmpty()  && teacherPhone.isNotEmpty() && imageUri != null) {
                uploadImageAndData(teacherName, teacherEcode,teacherRole,teacherRoom,teacherEmail,teacherPhone)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            scheduleImageView?.setImageURI(imageUri)
        }
    }

    private fun uploadImageAndData(teacherName: String, teacherEcode: String,teacherRole:String,teacherRoom:String,teacherEmail:String,teacherPhone:String) {
        val imageFileName = UUID.randomUUID().toString()
        val imageRef = storageReference.child(imageFileName)

        imageUri?.let { uri ->
            try {
                imageRef.putFile(uri)
                    .addOnSuccessListener {
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            try {
                                val imageUrl = uri.toString()
                                val schedule = Schedule(
                                    teacherEcode = teacherEcode ,
                                    teacherName = teacherName ,
                                    teacherSittingRoom = teacherRoom,
                                    teacherRole = teacherRole,
                                    teacherContact = teacherPhone,
                                    teacherEmail = teacherEmail,
                                    teacherTimetable = imageUrl
                                )
                                databaseReference.push().setValue(schedule)

                                scheduleList.add(schedule)
                                scheduleAdapter.notifyDataSetChanged()

                            } catch (e: Exception) {
                                Toast.makeText(requireContext(), "Error processing image URL", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error uploading image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateScheduleList() {
        try {
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    try {
                        scheduleList.clear()
                        for (snapshot in dataSnapshot.children) {
                            try {
                                val schedule = snapshot.getValue(Schedule::class.java)
                                schedule?.let { scheduleList.add(it) }
                            } catch (e: Exception) {
                                Toast.makeText(requireContext(), "Error processing schedule data", Toast.LENGTH_SHORT).show()
                            }
                        }
                        scheduleAdapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error updating schedule list", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(requireContext(), "Data is null", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error accessing database", Toast.LENGTH_SHORT).show()
        }
    }

}
