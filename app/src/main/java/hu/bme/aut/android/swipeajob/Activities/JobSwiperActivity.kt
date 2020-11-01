package hu.bme.aut.android.swipeajob.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter.JobSwiperFragmentPagerAdapter
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperFragment
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperJobMatchesListFragment
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.activity_job_swiper.*


class JobSwiperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_swiper)
        setSupportActionBar(findViewById(R.id.toolbar))



        pager.adapter = JobSwiperFragmentPagerAdapter(this)
        pager.registerOnPageChangeCallback(TabLayoutOnPageChangeCallback(fab))
        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = when (position) {
                0 -> JobSwiperFragment.PAGE_TITLE
                1 -> JobSwiperJobMatchesListFragment.PAGE_TITLE
                else -> JobSwiperFragment.PAGE_TITLE
            }
        }.attach()

        pager.isUserInputEnabled = false



        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    class TabLayoutOnPageChangeCallback(val fab: FloatingActionButton) :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

            when (position) {
                0 -> fab.show()
                1 -> fab.hide()
                else -> fab.show()
            }
        }
    }


}

