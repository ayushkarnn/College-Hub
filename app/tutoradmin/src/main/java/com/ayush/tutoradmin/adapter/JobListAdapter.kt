package com.ayush.tutoradmin.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayush.tutoradmin.R
import com.ayush.tutoradmin.datamodel.Job

class JobListAdapter(
    private var listofJobs: List<Job>
) : RecyclerView.Adapter<JobListAdapter.JobViewHolder>() {

    class JobViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTV)
        val desc: TextView = itemView.findViewById(R.id.descriptionTV)
        val link: TextView = itemView.findViewById(R.id.linkTV)
        val dt: TextView = itemView.findViewById(R.id.postedDt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_layout_job, parent, false)
        return JobViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listofJobs.size
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentJob = listofJobs[position]
        holder.title.text = currentJob.title
        holder.desc.text = currentJob.description
        holder.link.text = currentJob.link
        holder.dt.text = currentJob.dt
        holder.link.setOnClickListener {
            val context = holder.itemView.context
            val uri = Uri.parse(currentJob.link)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            context.startActivity(intent)
        }
    }

}
