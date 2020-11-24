package hu.bme.aut.android.swipeajob.Data.QueryHelperClasses

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import hu.bme.aut.android.swipeajob.Data.CrossReferences.RightSwipedJobSearchersCrossRef
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher

data class MatchesForJobSearcher(
    @Embedded val jobSearcher: JobSearcher,
    @Relation(parentColumn = "jobsearcherid",
        entityColumn = "jobproviderid",
        associateBy = Junction(RightSwipedJobSearchersCrossRef::class)
    )
    val jobproviderMatches: List<JobProvider>,

    @Relation(
        parentColumn = "jobsearcherid",
        entityColumn = "jobid",
        associateBy = Junction(RightSwipedJobSearchersCrossRef::class)
    )
    val jobMathces: List<Job>




)