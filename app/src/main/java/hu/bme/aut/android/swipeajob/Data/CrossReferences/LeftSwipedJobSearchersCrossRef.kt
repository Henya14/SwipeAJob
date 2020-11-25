package hu.bme.aut.android.swipeajob.Data.CrossReferences

import androidx.room.Entity

@Entity( primaryKeys = ["jobsearcherid", "jobproviderid"])
data class LeftSwipedJobSearchersCrossRef(
    val jobsearcherid: Long,
    val jobproviderid: Long,
    val jobid: Long
)