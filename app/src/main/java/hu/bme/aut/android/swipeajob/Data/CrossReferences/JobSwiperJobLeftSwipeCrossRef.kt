package hu.bme.aut.android.swipeajob.Data.CrossReferences

import androidx.room.Entity

@Entity(primaryKeys = ["jobsearcherid", "jobid"])
data class JobSwiperJobLeftSwipeCrossRef(
    val jobsearcherid: Long,
    val jobid: Long
)