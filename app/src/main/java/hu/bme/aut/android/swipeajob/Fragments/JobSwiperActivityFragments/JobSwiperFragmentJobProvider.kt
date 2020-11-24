package hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.yuyakaido.android.cardstackview.*
import hu.bme.aut.android.swipeajob.Adapters.CardStackViewAdapter.CardStackAdapterJobProvider
import hu.bme.aut.android.swipeajob.Data.CrossReferences.LeftSwipedJobSearchersCrossRef
import hu.bme.aut.android.swipeajob.Data.CrossReferences.RightSwipedJobSearchersCrossRef
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.JobSearcherWithJobNameAndId
import hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.DialogFragments.JobSearcherDetailsDialogFragment
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.fragment_job_swiper_common_layout.*
import kotlin.concurrent.thread

class JobSwiperFragmentJobProvider(val username: String) : Fragment() , CardStackListener,
    CardStackAdapterJobProvider.JobSearcherItemClickedListener {


    private val manager by lazy { CardStackLayoutManager(context, this) }
    private val adapter by lazy { CardStackAdapterJobProvider(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_swiper_common_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardStackView.layoutManager = CardStackLayoutManager(context)
        loadJobSearchers()
        //setupNavigation()
        setupCardStackView()
        setupButton()
    }

    private fun loadJobSearchers()
    {
        var searchers = mutableListOf<JobSearcherWithJobNameAndId>()
        //TODO tranzakcióba rakni és átírni, hogy melyik jobsearchereket ne lássa
        thread {

            val swipedjobs = AppDatabase.getInstance(requireContext()).jobDao().getJobsThatWereSwipped()
            val providerandjobs = AppDatabase.getInstance(requireContext()).jobproviderDao().getJobProviderWithJobsWithUserName(username)



            val swipedApplicants = AppDatabase.getInstance(requireContext())
                .jobproviderDao()
                .getSwipedJobSearchersForJobProviderWithUsername(username)

            val applicantsThatWereSwipedIds = mutableListOf<Long>()
            swipedApplicants.jobsearchersThatWereSwipedLeft.forEach{applicantsThatWereSwipedIds.add(it.jobsearcherId!!)}
            swipedApplicants.jobsearchersThatWereSwipedRight.forEach{applicantsThatWereSwipedIds.add(it.jobsearcherId!!)}

            for( j in providerandjobs.jobs)
            {
                val jobThatWasSwiped =  swipedjobs.find { it.job.id == j.id}

                jobThatWasSwiped?.jobsearchersThatSwipedRightTheJob?.forEach{
                    if(!(it.jobsearcherId in applicantsThatWereSwipedIds)) {
                        searchers.add(JobSearcherWithJobNameAndId(jobsearcher = it, jobName = j.name, jobid = j.id!!))
                    }
                }

            }
            requireActivity().runOnUiThread{
                adapter.setJobSearchers(searchers)
            }

        }

    }




    companion object{
        const val PAGE_TITLE = "Applicants"
    }



    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")

        when (direction)
        {
            Direction.Left -> leftSwipe()
            Direction.Right -> rightSwipe()
            else -> return
        }
    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View, position: Int) {


    }

    override fun onCardDisappeared(view: View, position: Int) {

    }



    private fun setupCardStackView() {
        initialize()
    }

    private fun setupButton() {

        skip_button.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)

            cardStackView.swipe()

        }



        like_button.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
    }

    private fun rightSwipe(topPosistionOffset : Int = 1) {
        val jobsearcherid = adapter.getJobSearcher(manager.topPosition - topPosistionOffset).jobsearcher.jobsearcherId
        val jobid = adapter.getJobSearcher(manager.topPosition - topPosistionOffset).jobid
        adapter.removeJobSearcher(manager.topPosition - topPosistionOffset)

        //TODO ez tranzakcióba
        thread{
            val jobproviderid = AppDatabase.getInstance(requireContext()).jobproviderDao().getJobProviderIdForUsername(username)

            val match = RightSwipedJobSearchersCrossRef(jobsearcherid = jobsearcherid!!,  jobproviderid= jobproviderid!!, jobid =  jobid!!)
            AppDatabase.getInstance(requireContext()).rightswipedjobsearcherscrosssrefDao().insert(match)

        }
    }

    private fun leftSwipe(topPosistionOffset : Int = 1) {
        val jobsearcherid = adapter.getJobSearcher(manager.topPosition - topPosistionOffset).jobsearcher.jobsearcherId
        adapter.removeJobSearcher(manager.topPosition - topPosistionOffset)

        thread {
            val jobproviderid = AppDatabase.getInstance(requireContext()).jobproviderDao().getJobProviderIdForUsername(username)

            val leftSwipedJobSearchersCrossRefRecord = LeftSwipedJobSearchersCrossRef(jobsearcherid = jobsearcherid!!, jobproviderid = jobproviderid!!)
            AppDatabase.getInstance(requireContext()).leftswipedjobsearcherscrossrefDao().insert(leftSwipedJobSearchersCrossRefRecord)
        }

    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }



    override fun JobSearcherClicked(jobsearcher: JobSearcher) {
        JobSearcherDetailsDialogFragment(jobsearcher).show(requireActivity().supportFragmentManager,JobSearcherDetailsDialogFragment.TAG)
    }


}