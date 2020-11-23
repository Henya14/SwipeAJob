package hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperFragment_jobsearcher
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperJobMatchesListFragment

class JobSwiperFragmentPagerAdapter_jobsearcher(fm: FragmentActivity) :  FragmentStateAdapter(fm)
{
    companion object{
        const val NUM_PAGES = 2

        fun getTabTextByPosition(position: Int): String = when (position) {
            0 -> JobSwiperFragment_jobsearcher.PAGE_TITLE
            1 -> JobSwiperJobMatchesListFragment.PAGE_TITLE
            else -> JobSwiperFragment_jobsearcher.PAGE_TITLE
        }

    }

    override fun getItemCount(): Int  = NUM_PAGES

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> JobSwiperFragment_jobsearcher()
        1 -> JobSwiperJobMatchesListFragment()
        else ->  JobSwiperFragment_jobsearcher()
    }


}