package hu.bme.aut.android.swipeajob.Data.CrossReferences

import androidx.room.Entity
import hu.bme.aut.android.swipeajob.Activities.JobSwiperActivityJobsearcher

@Entity( primaryKeys = ["jobsearcherid", "jobproviderid", "jobid"])
data class RightSwipedJobSearchersCrossRef(
    val jobsearcherid: Long,
    val jobproviderid: Long,
    val jobid: Long
)