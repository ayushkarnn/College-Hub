package app.tutorbyte

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.tutorbyte.adapter.TeacherScheduleAdapter
import app.tutorbyte.helper.Schedule
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

class TeacherSchedule : Fragment() {

    private lateinit var myRv: RecyclerView
    private lateinit var edit: EditText
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private val scheduleList = mutableListOf<Schedule>()
    private lateinit var scheduleAdapter: TeacherScheduleAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_teacher_schedule, container, false)

        myRv = view.findViewById(R.id.myRv)
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
