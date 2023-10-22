package app.tutorbyte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {


    private var selectedCardId: Int = -1

    private fun setupCardClickListener(card: CardView, actionId: Int) {
        card.setOnClickListener {
            selectedCardId = card.id
            findNavController().navigate(actionId)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.black)
        activity?.window?.statusBarColor = statusBarColor
        val view = inflater.inflate(R.layout.fragment_main, container, false)


        if (savedInstanceState != null) {
            selectedCardId = savedInstanceState.getInt("selectedCardId", -1)
        }

        val card1 = view.findViewById<CardView>(R.id.card1)
        val card2 = view.findViewById<CardView>(R.id.card2)
        val card3 = view.findViewById<CardView>(R.id.card3)
        val card4 = view.findViewById<CardView>(R.id.card4)
        val card5 = view.findViewById<CardView>(R.id.card5)
        val card6 = view.findViewById<CardView>(R.id.card6)
        val card7 = view.findViewById<CardView>(R.id.card7)
        val card8 = view.findViewById<CardView>(R.id.card8)
        val card9 = view.findViewById<CardView>(R.id.card9)
        val card11 = view.findViewById<CardView>(R.id.card11)
//        val cardjob =view.findViewById<CardView>(R.id.cardJobUpdates)

        setupCardClickListener(card1, R.id.action_mainFragment_to_listFragment)
        setupCardClickListener(card2, R.id.action_mainFragment_to_androidListFragment)
        setupCardClickListener(card3, R.id.action_mainFragment_to_webdFragment)
        setupCardClickListener(card4, R.id.action_mainFragment_to_aiMlFragment)
        setupCardClickListener(card5, R.id.action_mainFragment_to_dbmsFragment)
        setupCardClickListener(card6, R.id.action_mainFragment_to_cyberSFragment)
        setupCardClickListener(card7, R.id.action_mainFragment_to_CNFragment)
        setupCardClickListener(card8, R.id.action_mainFragment_to_osFragment)
        setupCardClickListener(card9, R.id.action_mainFragment_to_othersFragment)
        setupCardClickListener(card11, R.id.action_mainFragment_to_contentSubmitFragment)
//        setupCardClickListener(cardjob,R.id.action_mainFragment_to_jobFragment)


        if (selectedCardId != -1) {
            view.findViewById<CardView>(selectedCardId).setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedCardId", selectedCardId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
