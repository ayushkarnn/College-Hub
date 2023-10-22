package app.tutorbyte.fragments.dsa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.tutorbyte.R
import soup.neumorphism.NeumorphCardView


class QuestiondsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_questionds, container, false)
        val leetcode = view.findViewById<NeumorphCardView>(R.id.cardView1)
        val otherds = view.findViewById<NeumorphCardView>(R.id.cardView2)
        leetcode.setOnClickListener {
            findNavController().navigate(R.id.action_questiondsFragment_to_dsLeetCodeFragment)
        }
        otherds.setOnClickListener {
            findNavController().navigate(R.id.action_questiondsFragment_to_leetcodeotherFragment2)
        }
        return view


    }


}