package hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobSearcher

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import com.yuyakaido.android.cardstackview.*
import hu.bme.aut.android.swipeajob.Adapters.CardStackViewAdapter.CardStackAdapterJobSearcher
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobLeftSwipeCrossRef
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobRightSwipeCrossRef
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.JobSearchersThatSwipedJobs
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.fragment_job_swiper_common_layout.*
import kotlin.concurrent.thread


class JobSwiperFragmentJobSearcher(val username: String) : Fragment() ,CardStackListener{


    private val manager by lazy { CardStackLayoutManager(context, this) }
    private val adapter by lazy { CardStackAdapterJobSearcher() }

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
        cardStackView.adapter = CardStackAdapterJobSearcher()

        greyLoadingScreen.visibility = View.VISIBLE
        loadingProgressBar.visibility = View.VISIBLE

        loadJobs()
        //setupNavigation()
        setupCardStackView()
        setupButton()
    }

    private fun loadJobs() {
        thread {
            val db = AppDatabase.getInstance(requireContext())
            var result = listOf<Job>()
            var swipedJobsForJobSearcher :JobSearchersThatSwipedJobs? = null
            val swipedJobsIDs = mutableListOf<Long>()
            db.runInTransaction {
                result = db.jobDao().getAllJobs()
                swipedJobsForJobSearcher = db.jobsearcherDao().getJobSearchersThatSwipedJobs().filter { it.jobSearcher.userName == username }[0]

            }

            swipedJobsForJobSearcher!!.jobsThatTheSearcherSwipedRight.forEach{ swipedJobsIDs.add(it.id!!) }
            swipedJobsForJobSearcher!!.jobsThatTheSearcherSwipedLeft.forEach{ swipedJobsIDs.add(it.id!!) }

            val notSwipedJobs = result.filter { (it.id !in swipedJobsIDs && !it.removed) }
            requireActivity().runOnUiThread{
                adapter.setJobs(notSwipedJobs)
                greyLoadingScreen.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
            }

        }
    }


    companion object{
        const val PAGE_TITLE = "Jobs"
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
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
        //val textView = view.findViewById<TextView>(R.id.)
        //Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
       // val textView = view.findViewById<TextView>(R.id.item_name)
        //Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
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

    private fun leftSwipe(topPosistionOffset : Int = 1) {

        val jobid = adapter.getJob(manager.topPosition - topPosistionOffset).id
        adapter.removeJob(manager.topPosition - topPosistionOffset)

        thread {
            val db = AppDatabase.getInstance(requireContext())
            db.runInTransaction {

                val jobSearcherid = db.jobsearcherDao()
                    .getAllJobSearchersWithUsername(username)[0].jobsearcherId

                val jobswiperJobLeftSwipeCrossRef =
                    JobSwiperJobLeftSwipeCrossRef(jobsearcherid = jobSearcherid!!, jobid = jobid!!)

               db.jobswiperJobLeftSwipeCrossRefDao()
                    .insert(jobswiperJobLeftSwipeCrossRef)
            }

        }
    }

    private fun rightSwipe(topPosistionOffset : Int = 1) {

        val jobid = adapter.getJob(manager.topPosition - topPosistionOffset).id
        adapter.removeJob(manager.topPosition - topPosistionOffset)

        thread {
            val db =  AppDatabase.getInstance(requireContext())
            db.runInTransaction {
                val jobSearcherid = db.jobsearcherDao()
                    .getAllJobSearchersWithUsername(username)[0].jobsearcherId

                val jobswiperJobRightSwipeCrossRef = JobSwiperJobRightSwipeCrossRef(jobsearcherid = jobSearcherid!!, jobid = jobid!!)

                db.jobswiperJobRightSwipeCrossRefDao().insert(jobswiperJobRightSwipeCrossRef)
            }

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



}