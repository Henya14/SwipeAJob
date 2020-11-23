package hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperFragmentJobprovider
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperFragmentJobsearcher
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperJobMatchesListFragment

class JobSwiperFragmentPagerAdapterJobprovider(fm: FragmentActivity) :  FragmentStateAdapter(fm)
{
    companion object{
        const val NUM_PAGES = 2

        fun getTabTextByPosition(position: Int): String = when (position) {
            0 -> JobSwiperFragmentJobsearcher.PAGE_TITLE
            1 -> JobSwiperJobMatchesListFragment.PAGE_TITLE
            else -> JobSwiperFragmentJobsearcher.PAGE_TITLE
        }

    }

    override fun getItemCount(): Int  = NUM_PAGES

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> JobSwiperFragmentJobprovider()
        1 -> JobSwiperJobMatchesListFragment()
        else ->  JobSwiperFragmentJobsearcher()
    }


}