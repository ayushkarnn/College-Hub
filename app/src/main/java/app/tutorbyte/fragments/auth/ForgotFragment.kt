package app.tutorbyte.fragments.auth

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.tutorbyte.R
import app.tutorbyte.helper.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ForgotFragment : Fragment() {

    private lateinit var Mauth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot, container, false)
        val loginTv =  view.findViewById<TextView>(R.id.regTv)
        loginTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        FirebaseAuth.getInstance()

        Mauth = Firebase.auth
        val emailEt = view.findViewById<EditText>(R.id.EdtEmail)
        val forgotBtn = view.findViewById<Button>(R.id.fbtn)

        forgotBtn.setOnClickListener {
            val email = emailEt.text.toString()
            if(email.isBlank()){
                Toast.makeText(requireContext(),"Email Can't Be Empty",Toast.LENGTH_SHORT).show()
            }else if(!isValidEmail(email)){
                Toast.makeText(requireContext(),"Please Enter Correct Email",Toast.LENGTH_SHORT).show()
            }else{
                _forgot(email)
            }
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun _forgot(email: String) {
            Mauth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Email Reset Link Sent Successfully",Toast.LENGTH_SHORT).show()
                    Toast.makeText(requireContext(),"Please Check Your Spam Folder Also",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.loginFragment)
                    updateDb(email)
                }
                .addOnFailureListener {
                        Toast.makeText(requireContext(),"Error-Check Credentials",Toast.LENGTH_SHORT).show()
                }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDb(email: String) {
        val a = getCurrentDateTimeAsString()
        val userRef = FirebaseDatabase.getInstance().getReference("${Constants.myVarReal.st}/${FirebaseAuth.getInstance().currentUser?.uid}")
        userRef.child("lastPasswordReset").setValue(a)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Forgot-UODT",Mauth.currentUser?.uid.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Forgot-UODT",error.toString())
            }
        })

    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTimeAsString(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return currentDateTime.format(formatter)
    }

}