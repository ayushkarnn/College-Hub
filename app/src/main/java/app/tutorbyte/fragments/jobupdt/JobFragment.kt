package app.tutorbyte.fragments.jobupdt

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.tutorbyte.R
import app.tutorbyte.helper.Job
import com.google.android.material.chip.Chip
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class JobFragment : Fragment() {

//    private lateinit var titleEt: EditText
//    private lateinit var descEt: EditText
//    private lateinit var linkEt: EditText
//    private lateinit var dtEt: EditText
//    private lateinit var btnSubmit: Button
    private lateinit var searchJobEt: EditText
    private lateinit var rv: RecyclerView
    private lateinit var jobAdapter: JobAdapter

    private lateinit var progressBar: ProgressBar

    private var listofJobs = mutableListOf<Job>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_job, container, false)

        val chipAmazon = view.findViewById<Chip>(R.id.chipAmazon)
        val chipGoogle = view.findViewById<Chip>(R.id.chipGoogle)
        val chipAtlassian = view.findViewById<Chip>(R.id.chipAtlassian)
        val chipRubrik = view.findViewById<Chip>(R.id.chipRubrik)
        val chipClickListener = View.OnClickListener {
            val chip = it as Chip
            val chipText = chip.text.toString()

            if (chip.isChecked) {
                chipAmazon.isChecked = false
                chipGoogle.isChecked = false
                chipAtlassian.isChecked = false
                chipRubrik.isChecked = false

                chip.isChecked = true

                searchJobEt.setText(chipText)
            } else {
                chip.isChecked = false
                searchJobEt.setText("")
            }
        }

        chipAmazon.setOnClickListener(chipClickListener)
        chipGoogle.setOnClickListener(chipClickListener)
        chipAtlassian.setOnClickListener(chipClickListener)
        chipRubrik.setOnClickListener(chipClickListener)

//        TODO(Animation not working)
//        val resid = R.anim.falldown

        rv = view.findViewById(R.id.myRv)
//        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        searchJobEt = view.findViewById(R.id.search_job)
        progressBar = view.findViewById(R.id.progressBar)
        showProgressBar()
//
//        val animation: LayoutAnimationController =
//            AnimationUtils.loadLayoutAnimation(requireContext(), resid)
//
//        rv.layoutAnimation = animation

        FirebaseDatabase.getInstance().getReference("Jobs")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    showProgressBar()

                    val tempList = mutableListOf<Job>()

                    for (dataSnapshot in snapshot.children) {
                        val job = dataSnapshot.getValue(Job::class.java)
                        if (job !== null) {
                            tempList.add(job)
                        }
                    }

                    if (tempList.isNotEmpty()) {
                        listofJobs.clear()
                        listofJobs.addAll(tempList)

                        if (::jobAdapter.isInitialized) {
                            jobAdapter.notifyDataSetChanged()
                        } else {
                            jobAdapter = JobAdapter(listofJobs)
                            rv.adapter = jobAdapter
                            rv.layoutManager = LinearLayoutManager(requireContext())
                        }
                    }

                    hideProgressBar()

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseError", "Error: ${error.message}")
                    hideProgressBar()
                }
            })


//        fab.setOnClickListener {
//            showDialog()
//        }

        searchJobEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterJobs(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }

    private fun filterJobs(query: String) {
        val filteredList = mutableListOf<Job>()
        CoroutineScope(Dispatchers.Default).launch {
            for (job in listofJobs) {
                if (job.title.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filteredList.add(job)
                }
            }
            withContext(Dispatchers.Main) {
                jobAdapter = JobAdapter(filteredList)
                rv.adapter = jobAdapter

            }
        }
    }


//    private fun showDialog() {
//        val dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(true)
//        dialog.setContentView(R.layout.custom_dialog)
//
//        titleEt = dialog.findViewById(R.id.edtTitle)
//        descEt = dialog.findViewById(R.id.edtDescription)
//        linkEt = dialog.findViewById(R.id.edtLink)
//        dtEt = dialog.findViewById(R.id.edtDate)
//        btnSubmit = dialog.findViewById(R.id.btnSubmit)
//
//        btnSubmit.setOnClickListener {
//            val title = titleEt.text.toString()
//            val desc = descEt.text.toString()
//            val link = linkEt.text.toString()
//            val date = dtEt.text.toString()
//            if(title.isBlank() || desc.isBlank() || link.isBlank() || date.isBlank()) {
//                Toast.makeText(requireContext(),"Please Fill All the Fields",Toast.LENGTH_SHORT).show()
//
//            }else{
//                uploadTOFirebase(title, desc, link, date)
//            }
//
//            dialog.dismiss()
//        }
//        dialog.show()
//    }
//
//    private fun uploadTOFirebase(title: String, desc: String, link: String, date: String) {
//        Firebase.database.getReference("Jobs").child(UUID.randomUUID().toString()).setValue(
//            Job(
//                title = title,
//                description = desc,
//                link = link,
//                dt = date
//            )
//        ).addOnSuccessListener {
//            Toast.makeText(requireContext(), "Added Successfully", Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
//            Toast.makeText(requireContext(), "Error Occurred", Toast.LENGTH_SHORT).show()
//        }
//    }
//
    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

}
