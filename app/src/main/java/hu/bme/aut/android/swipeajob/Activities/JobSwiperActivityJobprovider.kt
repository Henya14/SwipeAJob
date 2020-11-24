package hu.bme.aut.android.swipeajob.Activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter.JobSwiperFragmentPagerAdapterJobprovider
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.DialogFragments.NewJobDialogFragment
import hu.bme.aut.android.swipeajob.R
import io.github.yavski.fabspeeddial.FabSpeedDial
import kotlinx.android.synthetic.main.activity_job_swiper_common_layout.*
import kotlinx.android.synthetic.main.activity_job_swiper_jobprovider.*
import kotlin.concurrent.thread
interface fabClickedListener
{
    fun postNewJobClicked()
}

class JobSwiperActivityJobprovider : AppCompatActivity(), NewJobDialogFragment.NewJobItemDialogListener, fabClickedListener {


    companion object{
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }

    lateinit var userName: String
    lateinit var jobProvider: JobProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userName = this.intent.getStringExtra(KEY_USER_NAME)?: throw Exception(getString(R.string.nousername_exception))

        thread {
            jobProvider = AppDatabase.getInstance(this).jobproviderDao().getJobProviderForUsername(userName)
        }

        setContentView(R.layout.activity_job_swiper_jobprovider)
        setSupportActionBar(findViewById(R.id.toolbar))


        title = "SwipeAJob"

        pager.adapter = JobSwiperFragmentPagerAdapterJobprovider(this, userName)


        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = JobSwiperFragmentPagerAdapterJobprovider.getTabTextByPosition(position)
        }.attach()

        pager.isUserInputEnabled = false
        pager.registerOnPageChangeCallback(
            TabLayoutOnPageChangeCallbackForSpeedDial(fab_Job_Provider)
        )

        fab_Job_Provider.setMenuListener(FabMenuListener(this))

    }

    inner class FabMenuListener(val listener: fabClickedListener):FabSpeedDial.MenuListener
    {
        override fun onPrepareMenu(p0: NavigationMenu?): Boolean {
            return true
        }

        override fun onMenuItemSelected(menuItem: MenuItem?): Boolean {
            when(menuItem!!.itemId)
            {
                R.id.action_post_new_job -> {
                    listener.postNewJobClicked()
                    return true
                }

                else -> return false
            }
        }

        override fun onMenuClosed() {

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

    override fun onJobItemCreated(newJob: Job) {
        thread {
            newJob.jobproviderId = jobProvider.id
            AppDatabase.getInstance(this).jobDao().insert(newJob)
        }
    }

    override fun postNewJobClicked() {
        NewJobDialogFragment(this, jobProvider.pictureUri).show(this.supportFragmentManager,NewJobDialogFragment.TAG)
    }
}