package app.tutorbyte.fragments.auth

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.tutorbyte.R
import app.tutorbyte.helper.Constants
import app.tutorbyte.helper.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class RegisterFragment : Fragment() {


    private lateinit var Mauth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
//        val redirect = view.findViewById<ImageView>(R.id.imgView)
        val loginTv = view.findViewById<TextView>(R.id.loginTV)
        val emailEt = view.findViewById<EditText>(R.id.edtEmail)
        val passwEt = view.findViewById<EditText>(R.id.edtPassword)
        val confPassEt = view.findViewById<EditText>(R.id.edtPassword2)
        val signupBt = view.findViewById<ImageView>(R.id.signupBt)


        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        activity?.window?.statusBarColor = statusBarColor
//        redirect.setOnClickListener {
//            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {

        }
        FirebaseAuth.getInstance()

        Mauth = Firebase.auth


        signupBt.setOnClickListener {
            val email  = emailEt.text.toString()
            val passw = passwEt.text.toString()
            val confpass = confPassEt.text.toString()
            if (email.isBlank() || passw.isBlank()){
                Toast.makeText(requireContext(),"Please Fill All The Fields",Toast.LENGTH_SHORT).show()
            }else if (!isValidEmail(email)){
                Toast.makeText(requireContext(),"Please Enter Valid Email",Toast.LENGTH_SHORT).show()
            }else if (!isValidPassword(passw)){
                Toast.makeText(requireContext(),"Password Must be of 8 Characters",Toast.LENGTH_SHORT).show()
            }else if((passw != confpass)){
                Toast.makeText(requireContext(),"Password Must be Same",Toast.LENGTH_SHORT).show()
            }else if(!validCuchd(email)){
                Toast.makeText(requireContext(),"Email Must be Cuchd only",Toast.LENGTH_SHORT).show()
            }else{
                _signup(email,passw)
            }

        }

//        signupBt.setOnClickListener {
//
//            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
//        }

        loginTv.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("_Email",emailEt.text.toString())
            findNavController().navigate(R.id.loginFragment)
        }


        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun _signup(email: String, passw: String) {
            Mauth.createUserWithEmailAndPassword(email,passw)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Signup Successfull",Toast.LENGTH_SHORT).show()
                    val user =User(
                        email = email,
                        signupDt = getCurrentDateTimeAsString(),
                        lastLogin = "NA",
                        lastPasswordReset = "NA",
                        loginCnt = "0",
                        isProfileCompleted = false
                    )
                    addusertoDb(user)
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Error Occured",Toast.LENGTH_SHORT).show()
                }
    }

    private  fun addusertoDb(user: User) {
        Firebase.database.getReference(Constants.myVarReal.st).child(Mauth.uid.toString()).setValue(user)
    }




    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validCuchd(email: String): Boolean {
        return email.endsWith("@cuchd.in")
    }


    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTimeAsString(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return currentDateTime.format(formatter)
    }






}
