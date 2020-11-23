package hu.bme.aut.android.swipeajob.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter.JobSwiperFragmentPagerAdapterJobprovider
import hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter.JobSwiperFragmentPagerAdapterJobsearcher
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.activity_job_swiper_common_layout.*
import kotlinx.android.synthetic.main.activity_job_swiper_jobprovider.*

class JobSwiperActivityJobprovider : AppCompatActivity() {

    companion object{
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }

    lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userName = this.intent.getStringExtra(KEY_USER_NAME)?: throw Exception(getString(R.string.nousername_exception))


        setContentView(R.layout.activity_job_swiper_jobprovider)
        setSupportActionBar(findViewById(R.id.toolbar))


        pager.adapter = JobSwiperFragmentPagerAdapterJobprovider(this)


        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = JobSwiperFragmentPagerAdapterJobsearcher.getTabTextByPosition(position)
        }.attach()

        pager.isUserInputEnabled = false
        pager.registerOnPageChangeCallback(
            TabLayoutOnPageChangeCallbackForSpeedDial(fab_Job_Provider)
        )


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