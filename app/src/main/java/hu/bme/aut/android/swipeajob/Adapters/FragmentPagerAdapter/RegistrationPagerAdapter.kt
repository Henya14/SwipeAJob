package hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.JobProviderRegistrationFragment
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.JobSearcherRegistrationFragment

class RegistrationPagerAdapter(fm: FragmentActivity) :  FragmentStateAdapter(fm) {
    companion object {
        const val NUM_PAGES = 2

        fun getTabTextByPosition(position: Int): String = when (position) {
            0 -> JobSearcherRegistrationFragment.PAGE_TITLE
            1 -> JobProviderRegistrationFragment.PAGE_TITLE
            else -> JobSearcherRegistrationFragment.PAGE_TITLE
        }
    }

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> JobSearcherRegistrationFragment()
        1 -> JobProviderRegistrationFragment()
        else -> JobSearcherRegistrationFragment()
    }




}