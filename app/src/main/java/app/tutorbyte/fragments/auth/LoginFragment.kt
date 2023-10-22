package app.tutorbyte.fragments.auth

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.tutorbyte.R
import app.tutorbyte.databinding.FragmentLoginBinding
import app.tutorbyte.helper.Constants
import app.tutorbyte.helper.LoginHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var Mauth: FirebaseAuth


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

//        val loginStateManager = LoginHelper(requireContext())
//        if (loginStateManager.isLoggedIn) {
//            findNavController().navigate(R.id.mainFragment2)
//        }else{
//            findNavController().navigate(R.id.action_loginFragment_to_profileCompletionFragment)
//        }

        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        activity?.window?.statusBarColor = statusBarColor


        binding.regTv.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        binding.forgotTV.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }

        FirebaseAuth.getInstance()

        Mauth = Firebase.auth



        binding.btnLogin.setOnClickListener {
            val email  = binding.edtEmail.text.toString()
            val passw = binding.edtPass.text.toString()
            if (email.isBlank() || passw.isBlank()){
                Toast.makeText(requireContext(),"Please Fill All The Fields", Toast.LENGTH_SHORT).show()
            }else if (!isValidEmail(email)){
                Toast.makeText(requireContext(),"Please Enter Valid Email", Toast.LENGTH_SHORT).show()
            }else if (!isValidPassword(passw)){
                Toast.makeText(requireContext(),"Password Must be of 8 Characters", Toast.LENGTH_SHORT).show()
            }else{
                _login(email,passw)
            }

        }


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun _login(email: String, passw: String) {
        Mauth.signInWithEmailAndPassword(email,passw)
            .addOnSuccessListener {

                val loggedInUser = Mauth.currentUser
                val profileCompletedRef = Firebase.database.getReference("${Constants.myVarReal.st}/${loggedInUser?.uid}/profileCompleted")
                profileCompletedRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val profileCompleted = snapshot.getValue(Boolean::class.java)
                        if (loggedInUser != null) {
                            if (!loggedInUser.isEmailVerified){
                                loggedInUser.sendEmailVerification()
                                Toast.makeText(
                                    requireContext(),
                                    "Please verify your email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else if (profileCompleted == true && loggedInUser.isEmailVerified) {
                                navigateToMainFragment2(email)
                                val loginStateManager = LoginHelper(requireContext())
                                loginStateManager.isLoggedIn = true
                            } else if(profileCompleted == false){
                                val loginStateManager = LoginHelper(requireContext())
                                loginStateManager.isLoggedIn = false
                                findNavController().navigate(R.id.action_loginFragment_to_profileCompletionFragment)
                            }else{
                                Toast.makeText(
                                    requireContext(),
                                    "Error Occured",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("LoginFragment", "Error getting profileCompleted value: $error")
                    }
                })

            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), "Error Occured", Toast.LENGTH_SHORT).show()
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDb(email: String) {
        val a = getCurrentDateTimeAsString()
        val userRef = FirebaseDatabase.getInstance().getReference("${Constants.myVarReal.st}/${FirebaseAuth.getInstance().currentUser?.uid}")
        userRef.child("lastLogin").setValue(a)
        userRef.child("loginCnt").setValue(Constants.AppState.cnt)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Forgot-UODT",Mauth.currentUser?.uid.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Forgot-UODT",error.toString())
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun navigateToMainFragment2(email: String) {
        Toast.makeText(requireContext(), "Login SuccessFull", Toast.LENGTH_SHORT).show()
        updateDb(email)
        Constants.AppState.cnt++
        val loginStateManager = LoginHelper(requireContext())
        loginStateManager.isLoggedIn = true
        val bundle = Bundle()
        bundle.putString("Email",email)
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment2, bundle)
    }
}
