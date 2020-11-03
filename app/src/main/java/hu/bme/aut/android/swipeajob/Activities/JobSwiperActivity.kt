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

        val fab =  findViewById<io.github.yavski.fabspeeddial.FabSpeedDial>(R.id.fab_Job_Provider)
        findViewById<FloatingActionButton>(R.id.fab_Job_Searcher).hide()


        pager.adapter = JobSwiperFragmentPagerAdapter(this)

        pager.registerOnPageChangeCallback(TabLayoutOnPageChangeCallbackForSpeedDial(fab))

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = JobSwiperFragmentPagerAdapter.getTabTextByPosition(position)
        }.attach()

        pager.isUserInputEnabled = false




        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    class TabLayoutOnPageChangeCallbackForNormalFab(val fab: FloatingActionButton) :
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

    class TabLayoutOnPageChangeCallbackForSpeedDial(val fab: io.github.yavski.fabspeeddial.FabSpeedDial) :
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

