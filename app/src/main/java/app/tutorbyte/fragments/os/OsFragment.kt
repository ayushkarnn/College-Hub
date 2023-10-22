package app.tutorbyte.fragments.os

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import app.tutorbyte.R

class OsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.list1)
        activity?.window?.statusBarColor = statusBarColor
        val view = inflater.inflate(R.layout.fragment_os, container, false)
        val report = view.findViewById<ImageView>(R.id.report)
        report.setOnClickListener {
            findNavController().navigate(R.id.action_osFragment_to_reportFragment)
        }
        return view
    }

}