package hu.bme.aut.android.swipeajob.Data.QueryHelperClasses

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobLeftSwipeCrossRef
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobRightSwipeCrossRef
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher

data class JobSearchersThatSwipedJobs(
    @Embedded val jobSearcher: JobSearcher,
    @Relation(parentColumn = "jobsearcherid",
    entityColumn = "jobid",
    associateBy = Junction(JobSwiperJobLeftSwipeCrossRef::class)
    )
    val jobsThatTheSearcherSwipedRight: List<Job>,

    @Relation(parentColumn = "jobsearcherid",
    entityColumn = "jobid",
    associateBy = Junction(JobSwiperJobRightSwipeCrossRef::class)
    )
    val jobsThatTheSearcherSwipedLeft: List<Job>


)