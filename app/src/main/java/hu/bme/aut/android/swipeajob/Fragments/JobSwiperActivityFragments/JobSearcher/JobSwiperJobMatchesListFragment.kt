package hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSearcher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.MatchesJobSearcherRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.Match
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.MatchesForJobSearcher
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.fragment_job_swiper_job_matches_list.*
import kotlin.concurrent.thread

class JobSwiperJobMatchesListFragment(val username: String) : Fragment() {

    private lateinit var adapter: MatchesJobSearcherRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_swiper_job_matches_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MatchesJobSearcherRecyclerViewAdapter(requireContext())
        matchesRecyclerView.adapter = adapter
        matchesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadMatches()
    }

    private fun loadMatches() {
        thread {

            val matches = mutableListOf<MatchesForJobSearcher>()
            val matchedJobs = AppDatabase
                .getInstance(requireContext())
                .jobsearcherDao()
                .getMatchedJobProvidersForJobSearcherWithUsername(username)


            for ( i in 0..matchedJobs.jobproviderMatches.size - 1)
            {
                requireActivity().runOnUiThread{
                    adapter.addItem(
                        Match(
                            imageUri = matchedJobs.jobproviderMatches[i].pictureUri,
                            jobname = matchedJobs.jobMathces[i].name,
                            jobsearchername = "",
                            jobsearcherPhoneNumber = "",
                            jobproviderPhoneNumber = matchedJobs.jobproviderMatches[i].phoneNumber
                        )
                    )

                }
            }
        }
    }

    companion object{
        const val PAGE_TITLE = "Matches"
    }
}