package app.tutorbyte

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.tutorbyte.helper.menudata


class MessMenuAdapter(
    private val listofmenu:List<menudata>
): RecyclerView.Adapter<MessMenuAdapter.MessViewHolder>() {

    class MessViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.imgmess)
        val banner = itemView.findViewById<TextView>(R.id.topTv)
        val mainmenu = itemView.findViewById<TextView>(R.id.menuu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_rv_mess,parent,false)
        return MessViewHolder(view)
    }

    override fun getItemCount(): Int {
       return listofmenu.size
    }

    override fun onBindViewHolder(holder: MessViewHolder, position: Int) {
        val currMenu = listofmenu[position]

        holder.image.setImageResource(currMenu.image)
        holder.banner.text = currMenu.banner
        holder.mainmenu.text =  currMenu.menu
    }

}

