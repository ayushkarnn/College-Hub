package app.tutorbyte.fragments.android

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


class AndroidListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.list1)
        activity?.window?.statusBarColor = statusBarColor
        val view = inflater.inflate(R.layout.fragment_android_list, container, false)
        val notes = view.findViewById<NeumorphCardView>(R.id.cardView1)
        val report = view.findViewById<ImageView>(R.id.report)
        val book = view.findViewById<NeumorphCardView>(R.id.book)
        val roadmap = view.findViewById<NeumorphCardView>(R.id.roadMap)
        val other = view.findViewById<NeumorphCardView>(R.id.other)
        notes.setOnClickListener {
            findNavController().navigate(R.id.action_androidListFragment_to_notesanFragment)
        }
        report.setOnClickListener {
            findNavController().navigate(R.id.action_androidListFragment_to_reportFragment)

        }
        roadmap.setOnClickListener {
            findNavController().navigate(R.id.action_androidListFragment_to_cheatanFragment)
        }
        book.setOnClickListener {
            findNavController().navigate(R.id.action_androidListFragment_to_androidBookFragment)
        }
        other.setOnClickListener {
            findNavController().navigate(R.id.action_androidListFragment_to_otheranFragment)
        }

        return view
    }


}