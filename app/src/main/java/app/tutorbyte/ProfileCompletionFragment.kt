package app.tutorbyte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.tutorbyte.helper.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ProfileCompletionFragment : Fragment() {

    private lateinit var auth:FirebaseAuth
    private lateinit var currUid:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_completion, container, false)

        auth = Firebase.auth

        val uid = view.findViewById<TextView>(R.id.edtUid)
        val name = view.findViewById<EditText>(R.id.edtName)
        val spinner = view.findViewById<Spinner>(R.id.intersets)
        val submitBt = view.findViewById<Button>(R.id.submitBt)

        val items = arrayOf("Andorid Development", "Web Development", "AI/Ml", "Cyber Security")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        val email = auth.currentUser?.email
        currUid = auth.currentUser?.uid.toString()
        val currEmail = getCharactersBeforeAt(email.toString())
        uid.text = currEmail


        submitBt.setOnClickListener {
            val _uid = currEmail
            val _name =name.text.toString()
            val interests = spinner.selectedItem.toString()
            if (_uid.isBlank() || _name.isBlank()){
                Toast.makeText(requireContext(),"Uid and Name Can't be Empty",Toast.LENGTH_SHORT).show()
            }else{
                addtoDb(_uid,_name,interests)
                findNavController().navigate(R.id.mainFragment2)

            }

        }


        return view
    }

    private fun addtoDb(_uid: String, _name: String, interests: String) {
        val profile = HashMap<String, Any>()
        profile["uid"] = _uid
        profile["name"] = _name
        profile["interests"] = interests


        Firebase.database.getReference(Constants.myVarReal.st).child(currUid).updateChildren(profile)
        setProfileCompleted(currUid)
    }


    private fun setProfileCompleted(currUid: String) {
        val profileCompletedRef = Firebase.database.getReference("${Constants.myVarReal.st}/$currUid/profileCompleted")
        profileCompletedRef.setValue(true)
    }




    private fun getCharactersBeforeAt(email: String): String {
        val atIndex = email.indexOf('@')
        return if (atIndex != -1) {
            email.substring(0, atIndex)
        } else {
            ""
        }
    }



}