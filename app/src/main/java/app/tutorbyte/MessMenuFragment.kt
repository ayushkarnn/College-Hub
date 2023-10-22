package app.tutorbyte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.tutorbyte.helper.menudata

class MessMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mess_menu, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.rv_mess)
        val menuList = listOf(
            menudata(R.drawable.breakfast, "Breakfast", "Chhole Bhature,Tea and Milk"),
            menudata(R.drawable.lunch, "Lunch", "Rice, Dal, Curry, Salad"),
            menudata(R.drawable.snack, "Snacks", "Samosa & Tea"),
            menudata(R.drawable.dinner, "Dinner", "Panner,Rice,Chappati,DalMakhani")
        )


        val adapter = MessMenuAdapter(menuList)

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())


        return view
    }
}
