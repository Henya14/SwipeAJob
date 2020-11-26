package hu.bme.aut.android.swipeajob.Activities.JobProvider

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.swipeajob.Activities.MainActivity
import hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter.JobSwiperFragmentPagerAdapterJobprovider
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobProvider.DialogFragments.NewJobDialogFragment
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobProvider.OnMatchesTabSelectedListener
import hu.bme.aut.android.swipeajob.R
import io.github.yavski.fabspeeddial.FabSpeedDial
import kotlinx.android.synthetic.main.activity_job_swiper_common_layout.*
import kotlinx.android.synthetic.main.activity_job_swiper_jobprovider.*
import kotlin.concurrent.thread
interface FabClickedListener
{
    fun postNewJobClicked()
    fun modifyInfoClicked()
}

class JobSwiperActivityJobProvider : AppCompatActivity(), NewJobDialogFragment.NewJobItemDialogListener,
    FabClickedListener {


    companion object{
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }

    private lateinit var userName: String
    var onMatchesTabSelectedListener :OnMatchesTabSelectedListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        userName = this.intent.getStringExtra(KEY_USER_NAME)?: throw Exception(getString(R.string.nousername_exception))



        setContentView(R.layout.activity_job_swiper_jobprovider)
        setSupportActionBar(findViewById(R.id.toolbar))


        title = "SwipeAJob"

        pager.adapter = JobSwiperFragmentPagerAdapterJobprovider(this, userName, this)


        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = JobSwiperFragmentPagerAdapterJobprovider.getTabTextByPosition(position)
        }.attach()

        pager.isUserInputEnabled = false
        pager.registerOnPageChangeCallback(
            TabLayoutOnPageChangeCallback(fab_Job_Provider)
        )

        fab_Job_Provider.setMenuListener(FabMenuListener(this))

    }

    private inner class FabMenuListener(private val listener: FabClickedListener):FabSpeedDial.MenuListener
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

                R.id.action_modify_data ->
                {
                    listener.modifyInfoClicked()
                    return true
                }
                //TODO utolso gombot implementalni dialog fragmentnek

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


    private inner class TabLayoutOnPageChangeCallback(private val fab: FabSpeedDial) :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

            when (position) {
                0 -> fab.show()
                1 -> {fab.hide()
                        onMatchesTabSelectedListener?.matchesTabWasSelected()
                        }
                else -> fab.show()
            }
        }
    }

    override fun onJobItemCreated(newJob: Job) {
        thread {
            val db =  AppDatabase.getInstance(this)
            db.runInTransaction {
                val jobProvider = db.jobproviderDao().getJobProviderForUsername(userName)
                newJob.jobproviderId = jobProvider.id
                db.jobDao().insert(newJob)
            }

        }
    }

    override fun postNewJobClicked() {
        NewJobDialogFragment(this).show(this.supportFragmentManager,NewJobDialogFragment.TAG)
    }

    override fun modifyInfoClicked() {
        val intent = Intent(this, ChangeInfoJobProviderActivity::class.java)
        intent.putExtra(ChangeInfoJobProviderActivity.KEY_USER_NAME, userName)
        startActivity(intent)
    }
}