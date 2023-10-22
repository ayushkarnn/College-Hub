package app.tutorbyte.fragments.dsa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.tutorbyte.R
import soup.neumorphism.NeumorphCardView


class DsOthersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_ds_others, container, false)
        val yt = view.findViewById<NeumorphCardView>(R.id.cardView1)
        val dsaSheets = view.findViewById<NeumorphCardView>(R.id.cardView2)
        yt.setOnClickListener {
            findNavController().navigate(R.id.action_dsOthersFragment_to_yt_DsFragment)
        }
        dsaSheets.setOnClickListener {
            findNavController().navigate(R.id.action_dsOthersFragment_to_dsaSheetsFragment)
        }
        return view
    }

}