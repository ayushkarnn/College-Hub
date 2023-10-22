package app.tutorbyte.fragments.dsa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import app.tutorbyte.R
import app.tutorbyte.databinding.FragmentYtDsBinding

class Yt_DsFragment : Fragment() {

    private lateinit var binding: FragmentYtDsBinding

    private var areCardsVisible = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYtDsBinding.inflate(inflater, container, false)
        val view = binding.root

        if (!areCardsVisible) {
            animateCards()
            areCardsVisible = true
        }


        binding.striverCardView.setOnClickListener {
            openUrl("https://yt.openinapp.co/s6hjk")
        }
        binding.loveBabbarCardView.setOnClickListener {
            openUrl("https://yt.openinapp.co/rao0u")
        }
        binding.arshGoyalCardView.setOnClickListener {
            openUrl("https://yt.openinapp.co/6liae")
        }
        binding.siddharthSinghCardView.setOnClickListener {
            openUrl("https://yt.openinapp.co/48zjw")
        }
        binding.leadCodingByFrazCardView.setOnClickListener {
            openUrl("https://yt.openinapp.co/2k4ur")
        }
        binding.theCodeSkoolCardView.setOnClickListener {
            openUrl("https://yt.openinapp.co/w1awb")
        }
        binding.apnaCollegeCardView.setOnClickListener {
            openUrl("https://yt.openinapp.co/4s1d1")
        }

        return view
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
        Toast.makeText(requireContext(), "Opening Youtube...", Toast.LENGTH_SHORT).show()
    }

    private fun animateCards() {
        val delayBetweenAnimations = 300L

        val cardViews = listOf(
            binding.striverCardView,
            binding.loveBabbarCardView,
            binding.arshGoyalCardView,
            binding.siddharthSinghCardView,
            binding.leadCodingByFrazCardView,
            binding.theCodeSkoolCardView,
            binding.apnaCollegeCardView
        )

        cardViews.forEachIndexed { index, cardView ->
            cardView.visibility = View.INVISIBLE
            cardView.postDelayed({
                if (isAdded && !isDetached) {
                    cardView.visibility = View.VISIBLE
                    val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
                    cardView.startAnimation(animation)
                }
            }, index * delayBetweenAnimations)
        }
    }
}