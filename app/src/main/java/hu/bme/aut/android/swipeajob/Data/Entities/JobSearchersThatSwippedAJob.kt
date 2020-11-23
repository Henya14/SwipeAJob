package hu.bme.aut.android.swipeajob.Data.Entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobRightSwipeCrossRef

data class JobSearchersThatSwippedAJob (
    @Embedded val job: Job,
    @Relation(parentColumn = "jobid",
                entityColumn = "jobsearcherid",
                associateBy = Junction(JobSwiperJobRightSwipeCrossRef::class)
    )
    val jobsearchersThatSwipedRight: List<JobSearcher>
)

