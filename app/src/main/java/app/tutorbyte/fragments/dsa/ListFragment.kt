package app.tutorbyte.fragments.dsa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.tutorbyte.R
import kotlinx.coroutines.DelicateCoroutinesApi

import soup.neumorphism.NeumorphCardView
import java.io.IOException

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.list1)
        activity?.window?.statusBarColor = statusBarColor
        val notesCd = view.findViewById<NeumorphCardView>(R.id.cardView1)
        val report = view.findViewById<ImageView>(R.id.report)
        val cheatcd = view.findViewById<NeumorphCardView>(R.id.cardView2)
        val questioncd = view.findViewById<NeumorphCardView>(R.id.cardView3)
        val other = view.findViewById<NeumorphCardView>(R.id.Other)

        notesCd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_notesFragment)
        }
        report.setOnClickListener {
            findNavController().navigate(R.id.reportFragment)
        }
        cheatcd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_cheatdsFragment)
        }
        questioncd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_questiondsFragment)
        }
        other.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_dsOthersFragment)
        }



        return view
    }

}
