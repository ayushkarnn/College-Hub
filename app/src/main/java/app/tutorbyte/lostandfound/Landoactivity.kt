package app.tutorbyte.lostandfound

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import app.tutorbyte.R
import app.tutorbyte.adapter.LostAndFoundAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class landoactivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: LostAndFoundAdapter
    private lateinit var tabLayout:TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landoactivity)
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        viewPagerAdapter = LostAndFoundAdapter(this)
        viewPager.adapter = viewPagerAdapter


        TabLayoutMediator(tabLayout,viewPager){ tab:TabLayout.Tab, position:Int ->
            when(position){
                0->tab.text = "Lost"
                1->tab.text = "Found"
            }
        }.attach()


    }
}