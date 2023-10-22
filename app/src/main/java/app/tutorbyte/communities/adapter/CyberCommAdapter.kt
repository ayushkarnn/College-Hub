package app.tutorbyte.communities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.tutorbyte.R
import app.tutorbyte.helper.Comments
import com.bumptech.glide.Glide

class CyberCommAdapter(
    private val listOfComments: List<Comments>
) : RecyclerView.Adapter<CyberCommAdapter.CyberCommViewHolder>() {

    class CyberCommViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgViewComment)
        val message: TextView = itemView.findViewById(R.id.messageTv)
        val commentedBy: TextView = itemView.findViewById(R.id.uploadedByTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CyberCommViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_layout_comment, parent, false)
        return CyberCommViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfComments.size
    }

    override fun onBindViewHolder(holder: CyberCommViewHolder, position: Int) {
        val currComment = listOfComments[position]

        if (currComment.imageLink.isNullOrEmpty()) {
            holder.image.visibility = View.GONE
        } else {
            Glide.with(holder.itemView.context)
                .load(currComment.imageLink)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.image)
        }

        holder.message.text = currComment.messgaeTxt
        holder.commentedBy.text = currComment.commentedBy
    }
}
