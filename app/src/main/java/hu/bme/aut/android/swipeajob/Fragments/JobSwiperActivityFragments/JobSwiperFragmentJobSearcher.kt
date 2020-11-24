package hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import com.yuyakaido.android.cardstackview.*
import hu.bme.aut.android.swipeajob.Adapters.CardStackViewAdapter.CardStackAdapterJobSearcher
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobLeftSwipeCrossRef
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobRightSwipeCrossRef
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.JobSwiperData.Spot
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
        loadJobs()
        //setupNavigation()
        setupCardStackView()
        setupButton()
    }

    private fun loadJobs() {
        val t = thread {
            val result = AppDatabase.getInstance(requireContext()).jobDao().getAllJobs()
            val swipedJobsForJobSearcher = AppDatabase.getInstance(requireContext()).jobsearcherDao().getJobSearchersThatSwipedJobs().filter { it.jobSearcher.userName == username }[0]

            val swipedJobsIDs = mutableListOf<Long>()

            swipedJobsForJobSearcher.jobsThatTheSearcherSwipedRight.forEach{ swipedJobsIDs.add(it.id!!) }
            swipedJobsForJobSearcher.jobsThatTheSearcherSwipedLeft.forEach{ swipedJobsIDs.add(it.id!!) }

            val notSwipedJobs = result.filter { !(it.id in swipedJobsIDs) }
            requireActivity().runOnUiThread{
                adapter.setJobs(notSwipedJobs)
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

            leftSwipe()

        }



        like_button.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()


            rightSwipe()
        }
    }

    private fun leftSwipe() {
        thread {

            val jobid = adapter.getJob(manager.topPosition - 1).id
            requireActivity().runOnUiThread{
                adapter.removeJob(manager.topPosition - 1)
            }

            val jobSearcherid = AppDatabase.getInstance(requireContext()).jobsearcherDao()
                .getAllJobSearchersWithUsername(username)[0].jobsearcherId
            val jobswiperJobLeftSwipeCrossRef =
                JobSwiperJobLeftSwipeCrossRef(jobsearcherid = jobSearcherid!!, jobid = jobid!!)
            AppDatabase.getInstance(requireContext()).jobswiperJobLeftSwipeCrossRefDao()
                .insert(jobswiperJobLeftSwipeCrossRef)
        }
    }

    private fun rightSwipe() {
        thread {

            val jobid = adapter.getJob(manager.topPosition - 1).id

            requireActivity().runOnUiThread {
                adapter.removeJob(manager.topPosition - 1)
            }

            val jobSearcherid = AppDatabase.getInstance(requireContext()).jobsearcherDao()
                .getAllJobSearchersWithUsername(username)[0].jobsearcherId
            val jobswiperJobRightSwipeCrossRef = JobSwiperJobRightSwipeCrossRef(jobsearcherid = jobSearcherid!!, jobid = jobid!!)
            AppDatabase.getInstance(requireContext()).jobswiperJobRightSwipeCrossRefDao().insert(jobswiperJobRightSwipeCrossRef)
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










    private fun createSpot(): Spot {
        return Spot(
            name = "Yasaka Shrine",
            city = "Kyoto",
            url = "https://source.unsplash.com/Xq1ntWruZQI/600x800"
        )
    }

    private fun createSpots(): List<Spot> {
        val spots = ArrayList<Spot>()
        spots.add(Spot(name = "Yasaka Shrine", city = "Kyoto", url = "https://source.unsplash.com/Xq1ntWruZQI/600x800"))
        spots.add(Spot(name = "Fushimi Inari Shrine", city = "Kyoto", url = "https://source.unsplash.com/NYyCqdBOKwc/600x800"))
        spots.add(Spot(name = "Bamboo Forest", city = "Kyoto", url = "https://source.unsplash.com/buF62ewDLcQ/600x800"))
        spots.add(Spot(name = "Brooklyn Bridge", city = "New York", url = "https://source.unsplash.com/THozNzxEP3g/600x800"))
        spots.add(Spot(name = "Empire State Building", city = "New York", url = "https://source.unsplash.com/USrZRcRS2Lw/600x800"))
        spots.add(Spot(name = "The statue of Liberty", city = "New York", url = "https://source.unsplash.com/PeFk7fzxTdk/600x800"))
        spots.add(Spot(name = "Louvre Museum", city = "Paris", url = "https://source.unsplash.com/LrMWHKqilUw/600x800"))
        spots.add(Spot(name = "Eiffel Tower", city = "Paris", url = "https://source.unsplash.com/HN-5Z6AmxrM/600x800"))
        spots.add(Spot(name = "Big Ben", city = "London", url = "https://source.unsplash.com/CdVAUADdqEc/600x800"))
        spots.add(Spot(name = "Great Wall of China", city = "China", url = "https://source.unsplash.com/AWh9C-QjhE4/600x800"))
        return spots
    }



}