package app.tutorbyte.lostandfound

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.tutorbyte.R
import com.bumptech.glide.Glide

class LostAdapter(
    private var listofLostItems:List<LostandFound>
): RecyclerView.Adapter<LostAdapter.LostViewHolder>() {


    class LostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val description: TextView = itemView.findViewById(R.id.description)
        val uploadedby: TextView = itemView.findViewById(R.id.uploadedBy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_layout_lost,parent,false)
        return LostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listofLostItems.size
    }

    override fun onBindViewHolder(holder: LostViewHolder, position: Int) {
        val curritem = listofLostItems[position]
        Glide.with(holder.itemView.context)
            .load(curritem.imageLink)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.image);

        holder.description.text = curritem.description
        holder.uploadedby.text = curritem.uploadedBy

    }

}