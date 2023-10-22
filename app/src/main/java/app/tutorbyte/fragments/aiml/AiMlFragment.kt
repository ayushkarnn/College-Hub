package app.tutorbyte.fragments.aiml

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

class AiMlFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.list1)
        activity?.window?.statusBarColor = statusBarColor
        val view = inflater.inflate(R.layout.fragment_ai_ml, container, false)
        val report = view.findViewById<ImageView>(R.id.report)
        val notes = view.findViewById<NeumorphCardView>(R.id.cardView1)
        val cheatS = view.findViewById<NeumorphCardView>(R.id.cardView2)
        val que = view.findViewById<NeumorphCardView>(R.id.cardView3)
        val other = view.findViewById<NeumorphCardView>(R.id.cardView4)
        report.setOnClickListener {
            findNavController().navigate(R.id.action_aiMlFragment_to_reportFragment)
        }
        notes.setOnClickListener {
            findNavController().navigate(R.id.action_aiMlFragment_to_aiMlNotesFragment)
        }
        cheatS.setOnClickListener {
            findNavController().navigate(R.id.action_aiMlFragment_to_aimlCheatFragment)
        }
        other.setOnClickListener {
            findNavController().navigate(R.id.action_aiMlFragment_to_aimlOtherFragment)
        }
        que.setOnClickListener {
            findNavController().navigate(R.id.action_aiMlFragment_to_questFragment)
        }

        return view
    }


}