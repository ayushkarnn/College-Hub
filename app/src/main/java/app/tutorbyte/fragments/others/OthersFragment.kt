package app.tutorbyte.fragments.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import app.tutorbyte.R


class OthersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.list1)
        activity?.window?.statusBarColor = statusBarColor
        return inflater.inflate(R.layout.fragment_others, container, false)
    }

}