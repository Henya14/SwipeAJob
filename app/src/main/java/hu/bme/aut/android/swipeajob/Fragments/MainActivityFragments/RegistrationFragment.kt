package hu.bme.aut.android.swipeajob.Fragments.MainActivityFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.swipeajob.Adapters.FragmentPagerAdapter.RegistrationPagerAdapter
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperFragment
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSwiperJobMatchesListFragment
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment: Fragment()
{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pager_registration.adapter = RegistrationPagerAdapter(requireActivity())

        TabLayoutMediator(tab_layout_registration, pager_registration){tab, position ->
            tab.text = RegistrationPagerAdapter.getTabTextByPosition(position)
        }.attach()
    }


}