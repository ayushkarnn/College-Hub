package app.tutorbyte.lostandfound

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.tutorbyte.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID


class LostFragment : Fragment() {

    private lateinit var myRv: RecyclerView
    private lateinit var fab: FloatingActionButton

    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private val lostList = mutableListOf<LostandFound>()
    private lateinit var lostAdapter: LostAdapter

    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

     private lateinit var auth: FirebaseAuth

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lost, container, false)

        myRv = view.findViewById(R.id.myRv)
        fab = view.findViewById(R.id.fab)

        auth = Firebase.auth


        databaseReference = FirebaseDatabase.getInstance().reference.child("LostItems")
        storageReference = FirebaseStorage.getInstance().reference.child("Lost_images")

        val layoutManager = GridLayoutManager(requireContext(), 2)
        myRv.layoutManager = layoutManager

        lostAdapter = LostAdapter(lostList)
        myRv.adapter = lostAdapter

        fab.setOnClickListener {
            openDialog()
        }

        updateList()

        return view
    }



    private fun openDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.lostdialog, null)
        val description = dialogView.findViewById<EditText>(R.id.edtDescription)
        val uploadedBy = dialogView.findViewById<TextView>(R.id.edt)
        uploadedBy.text = auth.currentUser?.email

        imageView = dialogView.findViewById(R.id.ImageView)
        val btnSubmit = dialogView.findViewById<Button>(R.id.btnSubmit)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()

        imageView.setOnClickListener {
            openGallery()
        }

        btnSubmit.setOnClickListener {
            val _description = description.text.toString()
            val _uploadedBy = uploadedBy.text.toString()

            if (_description.isNotEmpty() && _uploadedBy.isNotEmpty() && imageUri != null) {
                uploadImageAndData(_description, _uploadedBy)
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
            imageView?.setImageURI(imageUri)
        }
    }

    private fun uploadImageAndData(description: String, uploadedBy: String) {
        val imageFileName = UUID.randomUUID().toString()
        val imageRef = storageReference.child(imageFileName)

        imageUri?.let { uri ->
            try {
                imageRef.putFile(uri)
                    .addOnSuccessListener {
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            try {
                                val imageUrl = uri.toString()
                                val lost = LostandFound(imageUrl, description, uploadedBy)
                                databaseReference.push().setValue(lost)

                                lostList.add(lost)
                                lostAdapter.notifyDataSetChanged()

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

    private fun updateList() {
        try {
            if (isAdded && context != null) {
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        try {
                            lostList.clear()
                            for (snapshot in dataSnapshot.children) {
                                try {
                                    val schedule = snapshot.getValue(LostandFound::class.java)
                                    schedule?.let { lostList.add(it) }
                                } catch (e: Exception) {
                                    Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                            lostAdapter.notifyDataSetChanged()
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), "Error updating list: ${e.message}", Toast.LENGTH_SHORT).show() // Display error updating list
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(requireContext(), "Data is null", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(requireContext(), "Fragment not attached or context is null", Toast.LENGTH_SHORT).show() // Display fragment not attached or context is null
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error accessing database: ${e.message}", Toast.LENGTH_SHORT).show() // Display error accessing database
        }
    }

}


