package hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.MatchesJobProviderRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.Match
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.fragment_job_swiper_job_matches_list.*
import kotlin.concurrent.thread

class JobSwiperApplicantMatchesListFragment(val username: String) : Fragment() {

    private lateinit var adapter: MatchesJobProviderRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_swiper_job_matches_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MatchesJobProviderRecyclerViewAdapter(requireContext())
        matchesRecyclerView.adapter = adapter
        matchesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadMatches()
    }

    private fun loadMatches() {

        thread {
            val matches = mutableListOf<Match>()
            val swipedApplicants = AppDatabase
                .getInstance(requireContext())
                .jobproviderDao()
                .getSwipedJobSearchersForJobProviderWithUsername(username)

            for (i in 0..swipedApplicants.jobsearchersThatWereSwipedRight.size - 1) {

                    matches.add(
                        Match(
                            imageUri = swipedApplicants.jobsearchersThatWereSwipedRight[i].pictureUri,
                            jobname = swipedApplicants.jobMathces[i].name,
                            jobsearchername = swipedApplicants.jobsearchersThatWereSwipedRight[i].fullname,
                            jobsearcherPhoneNumber = swipedApplicants.jobsearchersThatWereSwipedRight[i].phoneNumber,
                            jobproviderPhoneNumber = ""
                        )
                    )

            }

            requireActivity().runOnUiThread {
                adapter.setItems(matches)
            }
        }



    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        when (hidden)
        {
            false -> loadMatches()
            else -> return
        }
    }

    companion object {
        const val PAGE_TITLE = "Matches"
    }
}