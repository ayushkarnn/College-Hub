package app.tutorbyte.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import app.tutorbyte.R


class ContentSubmitFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val statusBarColor = ContextCompat.getColor(requireContext(), R.color.cust1)
        activity?.window?.statusBarColor = statusBarColor
        val view = inflater.inflate(R.layout.fragment_content_submit, container, false)
        val infoimg = view.findViewById<ImageView>(R.id.info)

        infoimg.setOnClickListener {
            showInfoDialog()
        }
        return view
    }

    private fun showInfoDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_info_dialog, null)
        val infoText = dialogView.findViewById<TextView>(R.id.info_text)
        val okButton = dialogView.findViewById<Button>(R.id.ok_button)

        infoText.text = "1.Your Content Will Be Uploaded After Some Days.\n\n2.Make Sure to Give Public Access to Google Drive Link.\n\n3.Don't Send multiple responses\n" +
                "\n" +
                "4.Paste your Portfolio So that We Can Give You Shoutout"

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        okButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}