package hu.bme.aut.android.swipeajob.Activities.JobSearcher

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.swipeajob.Activities.MainActivity
import hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter.JobSwiperFragmentPagerAdapterJobsearcher
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.activity_job_swiper_common_layout.*
import kotlinx.android.synthetic.main.activity_job_swiper_jobsearcher.*


class JobSwiperActivityJobsearcher : AppCompatActivity() {

    companion object{
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }

    lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        userName = this.intent.getStringExtra(KEY_USER_NAME) ?:  throw Exception(getString(R.string.nousername_exception))

        setContentView(R.layout.activity_job_swiper_jobsearcher)
        setSupportActionBar(findViewById(R.id.toolbar))


        title = "SwipeAJob"


        pager.adapter = JobSwiperFragmentPagerAdapterJobsearcher(this, userName)


        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = JobSwiperFragmentPagerAdapterJobsearcher.getTabTextByPosition(position)
        }.attach()

        pager.isUserInputEnabled = false


        pager.registerOnPageChangeCallback(
            TabLayoutOnPageChangeCallbackForNormalFab(
                fab_Job_Searcher
            )
        )

        fab_Job_Searcher.setOnClickListener {
            val intent = Intent(this, ChangeInfoJobSearcherActivity::class.java)
            intent.putExtra(ChangeInfoJobSearcherActivity.KEY_USER_NAME, userName)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.jobswipermenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.LogOut -> {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
            true
        }
        else ->  super.onOptionsItemSelected(item)
    }

    class TabLayoutOnPageChangeCallbackForNormalFab(private val fab: FloatingActionButton) :
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

