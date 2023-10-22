package app.tutorbyte.fragments.webd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import app.tutorbyte.R
import soup.neumorphism.NeumorphCardView


class QuestionwebFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_questionweb, container, false)
        val cd1 = view.findViewById<NeumorphCardView>(R.id.cardView1)
        val cd2 = view.findViewById<NeumorphCardView>(R.id.cardView2)
        cd1.setOnClickListener {
            findNavController().navigate(R.id.action_questionwebFragment_to_interviewWebdFragment)
        }
        cd2.setOnClickListener {
            Toast.makeText(requireContext(), "Will Be Added Soon", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_questionwebFragment_to_otherqWebdFragmentFragment)
        }
        return view
    }


}