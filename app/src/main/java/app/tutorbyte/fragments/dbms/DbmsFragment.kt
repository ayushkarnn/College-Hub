package app.tutorbyte.fragments.dbms

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


class DbmsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.list1)
        activity?.window?.statusBarColor = statusBarColor
        val view = inflater.inflate(R.layout.fragment_dbms, container, false)
        val report = view.findViewById<ImageView>(R.id.report)
        val notes = view.findViewById<NeumorphCardView>(R.id.cardView1)
        val cheatsh = view.findViewById<NeumorphCardView>(R.id.cardView2)
        val question = view.findViewById<NeumorphCardView>(R.id.cardView3)
        val more = view.findViewById<NeumorphCardView>(R.id.cardView4)
        report.setOnClickListener {
            findNavController().navigate(R.id.action_dbmsFragment_to_reportFragment)
        }

        notes.setOnClickListener {
            findNavController().navigate(R.id.action_dbmsFragment_to_notesDbmsFragment)
        }
        cheatsh.setOnClickListener {
            findNavController().navigate(R.id.action_dbmsFragment_to_cheatDbmsFragment)
        }
        question.setOnClickListener {
            findNavController().navigate(R.id.action_dbmsFragment_to_questionDbmsFragment)
        }
        more.setOnClickListener {
            findNavController().navigate(R.id.action_dbmsFragment_to_otherDbmsFragment)
        }
        return view
    }


}