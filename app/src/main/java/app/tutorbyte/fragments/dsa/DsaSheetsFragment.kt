package app.tutorbyte.fragments.dsa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import app.tutorbyte.R
import app.tutorbyte.databinding.FragmentDsaSheetsBinding

class DsaSheetsFragment : Fragment() {

    private lateinit var binding: FragmentDsaSheetsBinding
    private var areCardsVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDsaSheetsBinding.inflate(inflater, container, false)
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.dsasheet)
        activity?.window?.statusBarColor = statusBarColor


        if (!areCardsVisible) {
            animateCards()
            areCardsVisible = true
        }

        binding.striverCardView.setOnClickListener {
            openUrl("https://takeuforward.org/strivers-a2z-dsa-course/strivers-a2z-dsa-course-sheet-2/")
        }
        binding.loveBabbarCardView.setOnClickListener {
            openUrl("https://drive.google.com/file/d/1FMdN_OCfOI0iAeDlqswCiC2DZzD4nPsb/view")
        }
        binding.arshGoyalCardView.setOnClickListener {
            openUrl("https://docs.google.com/spreadsheets/d/1MGVBJ8HkRbCnU6EQASjJKCqQE8BWng4qgL0n3vCVOxE/htmlview?usp=sharing&pru=AAABgKkdtIE*rPv8dPkWyOpfwjprKvKSeA")
        }
        binding.siddharthSinghCardView.setOnClickListener {
            openUrl("https://docs.google.com/spreadsheets/u/0/d/11tevcTIBQsIvRKIZLbSzCeN4mCO6wD4O5meyrAIfSXw/htmlview\n")
        }
        binding.leadCodingByFrazCardView.setOnClickListener {
            openUrl("https://docs.google.com/spreadsheets/u/0/d/1-wKcV99KtO91dXdPkwmXGTdtyxAfk1mbPXQg81R9sFE/htmlview")
        }
        binding.theCodeSkoolCardView.setOnClickListener {
            openUrl("https://docs.google.com/document/u/0/d/1RxKKXJtErQFJjMfAh1kV-DyQsZoiESayimFx6PPIhVE/mobilebasic")
        }
        binding.apnaCollegeCardView.setOnClickListener {
            openUrl("https://docs.google.com/spreadsheets/u/0/d/1hXserPuxVoWMG9Hs7y8wVdRCJTcj3xMBAEYUOXQ5Xag/htmlview\n")
        }

        return binding.root
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
        Toast.makeText(requireContext(), "Opening Link...", Toast.LENGTH_SHORT).show()
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


