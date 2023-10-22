package app.tutorbyte.fragments.webd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import app.tutorbyte.R
import soup.neumorphism.NeumorphCardView
import soup.neumorphism.NeumorphImageView

class WebdFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.list1)
        activity?.window?.statusBarColor = statusBarColor
        val view = inflater.inflate(R.layout.fragment_webd, container, false)
        val report = view.findViewById<ImageView>(R.id.report)
        val notes = view.findViewById<NeumorphCardView>(R.id.cardView1)
        val cheatsheet = view.findViewById<NeumorphCardView>(R.id.cheatS)
        val ques = view.findViewById<NeumorphCardView>(R.id.ques)
        report.setOnClickListener {
            findNavController().navigate(R.id.reportFragment)
        }
        notes.setOnClickListener {
            findNavController().navigate(R.id.action_webdFragment_to_noteswebdFragment)
        }
        cheatsheet.setOnClickListener {
            findNavController().navigate(R.id.action_webdFragment_to_cheatWebdFragment)
        }
        ques.setOnClickListener {
            findNavController().navigate(R.id.action_webdFragment_to_questionwebFragment)
        }
        return view
    }


}