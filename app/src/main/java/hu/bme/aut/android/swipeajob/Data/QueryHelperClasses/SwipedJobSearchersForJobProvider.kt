package hu.bme.aut.android.swipeajob.Data.QueryHelperClasses

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import hu.bme.aut.android.swipeajob.Data.CrossReferences.LeftSwipedJobSearchersCrossRef
import hu.bme.aut.android.swipeajob.Data.CrossReferences.RightSwipedJobSearchersCrossRef
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher


data class SwipedJobSearchersForJobProvider (
    @Embedded val jobprovider: JobProvider,
    @Relation(parentColumn = "jobproviderid",
    entityColumn = "jobsearcherid",
    associateBy = Junction(RightSwipedJobSearchersCrossRef::class)
    )
    val jobsearchersThatWereSwipedRight: List<JobSearcher>,

    @Relation(
        parentColumn = "jobproviderid",
        entityColumn = "jobid",
        associateBy = Junction(RightSwipedJobSearchersCrossRef::class)
    )
    val jobMathces: List<Job>,

    @Relation(parentColumn = "jobproviderid",
    entityColumn = "jobsearcherid",
    associateBy = Junction(LeftSwipedJobSearchersCrossRef::class)
    )
    val jobsearchersThatWereSwipedLeft: List<JobSearcher>,

    @Relation(
        parentColumn = "jobproviderid",
        entityColumn = "jobid",
        associateBy = Junction(LeftSwipedJobSearchersCrossRef::class)
    )
    val jobsThatAreAssociatedWithJobSearchersThatWereSwipedLeft: List<Job>

)