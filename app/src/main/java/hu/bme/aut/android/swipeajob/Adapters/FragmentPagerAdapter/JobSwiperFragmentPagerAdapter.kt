package hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperFragment
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperJobMatchesListFragment

class JobSwiperFragmentPagerAdapter(fm: FragmentActivity) :  FragmentStateAdapter(fm)
{
    companion object{
        const val NUM_PAGES = 2


    }

    override fun getItemCount(): Int  = NUM_PAGES

    override fun createFragment(position: Int): Fragment = when(position) {
    0 -> JobSwiperFragment()
    1 -> JobSwiperJobMatchesListFragment()
    else ->  JobSwiperFragment()
    }


}