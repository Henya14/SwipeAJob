package hu.bme.aut.android.swipeajob.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter.JobSwiperFragmentPagerAdapter_jobsearcher
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.activity_job_swiper_common_layout.*
import kotlinx.android.synthetic.main.activity_job_swiper_jobsearcher.*


class JobSwiperActivity_jobsearcher : AppCompatActivity() {

    companion object{
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }

    lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userName = this.intent.getStringExtra(KEY_USER_NAME) ?:  throw Exception(getString(R.string.nousername_exception))

        setContentView(R.layout.activity_job_swiper_jobsearcher)
        setSupportActionBar(findViewById(R.id.toolbar))


        pager.adapter = JobSwiperFragmentPagerAdapter_jobsearcher(this)


        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = JobSwiperFragmentPagerAdapter_jobsearcher.getTabTextByPosition(position)
        }.attach()

        pager.isUserInputEnabled = false


        pager.registerOnPageChangeCallback(
            TabLayoutOnPageChangeCallbackForNormalFab(
                fab_Job_Searcher
            )
        )

        fab_Job_Searcher.setOnClickListener { view ->
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

}

