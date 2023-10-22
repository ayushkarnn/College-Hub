package app.tutorbyte

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.tutorbyte.helper.Constants
import app.tutorbyte.lostandfound.landoactivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import soup.neumorphism.NeumorphCardView

class MainFragment2 : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var currUid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main2, container, false)

        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.fornew)
        activity?.window?.statusBarColor = statusBarColor

        auth = Firebase.auth
        currUid = auth.currentUser?.uid.toString()

        val notescd = view.findViewById<NeumorphCardView>(R.id.cardView1)
        val teachersch = view.findViewById<NeumorphCardView>(R.id.cardView2)
        val jobupdt = view.findViewById<NeumorphCardView>(R.id.cardView3)
        val other = view.findViewById<NeumorphCardView>(R.id.cardView4)

        val userName = view.findViewById<TextView>(R.id.textview1)
        val imageView3 = view.findViewById<ImageView>(R.id.imageView3)

        val messMenu = view.findViewById<Button>(R.id.messMenu)
        messMenu.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment2_to_messMenuFragment)
        }

        val userRef = Firebase.database.getReference("${Constants.myVarReal.st}/$currUid")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = dataSnapshot.child("name").value as String?
                val interests = dataSnapshot.child("interests").value as String?

                if (!name.isNullOrBlank()) {
                    userName.text = name
                }

                imageView3.setOnClickListener {
                    if (!interests.isNullOrBlank()) {
                        Toast.makeText(requireContext(), interests, Toast.LENGTH_SHORT).show()
                        when (interests) {
                            "Cyber Security" -> findNavController().navigate(R.id.action_mainFragment2_to_cyberSecurityComm)
                            "Andorid Development" -> findNavController().navigate(R.id.action_mainFragment2_to_androidDevelopmentComm3)
                            "AI/Ml" -> findNavController().navigate(R.id.action_mainFragment2_to_aiMlComm)
                            "Web Development" -> findNavController().navigate(R.id.action_mainFragment2_to_webDevelopmentFragment)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        notescd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment2_to_mainFragment)
        }

        teachersch.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment2_to_teacherSchedule)
        }

        jobupdt.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment2_to_jobFragment)
        }

        other.setOnClickListener {
            val intent = Intent(requireContext(), landoactivity::class.java)
            startActivity(intent)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }

        return view
    }
}
