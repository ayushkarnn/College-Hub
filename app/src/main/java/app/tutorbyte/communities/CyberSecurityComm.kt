package app.tutorbyte.communities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.tutorbyte.R
import app.tutorbyte.communities.adapter.CyberCommAdapter
import app.tutorbyte.helper.Comments
import app.tutorbyte.helper.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class CyberSecurityComm : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var _username: String
    private var selectedImageUri: Uri? = null
    private val commentList = mutableListOf<Comments>()
    private lateinit var cyberCommAdapter: CyberCommAdapter
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var inputMessage: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cyber_security_comm, container, false)
        Toast.makeText(requireContext(), "Select + Icon To Upload The Image", Toast.LENGTH_SHORT).show()
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        val currUid = auth.currentUser?.uid

        val rv = view.findViewById<RecyclerView>(R.id.myRvcomment)
        val selectImg = view.findViewById<ImageView>(R.id.galleryImgSelect)
        val sendBtn = view.findViewById<ImageView>(R.id.sendImageView)
        inputMessage = view.findViewById(R.id.inputmessage)

        cyberCommAdapter = CyberCommAdapter(commentList)
        rv.adapter = cyberCommAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        val userRef = Firebase.database.getReference("${Constants.myVarReal.st}/$currUid")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _username = (dataSnapshot.child("name").value as String?).toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        selectImg.setOnClickListener {
            openGallery()
        }

        sendBtn.setOnClickListener {
            uploadComment()
        }
        loadComments()

        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            Toast.makeText(requireContext(), selectedImageUri.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadComment() {
        val commentText = inputMessage.text.toString().trim()
        if (commentText.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a comment", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser = auth.currentUser
        currentUser?.uid
        val username = currentUser?.email

        val questionRef = database.child("CyberSecurity")

        val commentId = UUID.randomUUID().toString()
        val commentRef = questionRef.child(commentId)

        if (selectedImageUri != null) {
            val imageRef = storageReference.child("images/$commentId")
            imageRef.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                        val comment = Comments(imageUrl.toString(), username!!, commentText)
                        commentRef.setValue(comment)
                        inputMessage.text.clear()
                        selectedImageUri = null
                        loadComments()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show()
                    loadComments()
                }
        } else {
            val comment = Comments(null, username!!, commentText)
            commentRef.setValue(comment)
            inputMessage.text.clear()
            loadComments()
        }
    }

    private fun loadComments() {
        val questionRef = database.child("CyberSecurity")

        questionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                commentList.clear()
                for (snapshot in dataSnapshot.children) {
                    val comment = snapshot.getValue(Comments::class.java)
                    comment?.let {
                        commentList.add(it)
                    }
                }
                cyberCommAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 123
    }
}
