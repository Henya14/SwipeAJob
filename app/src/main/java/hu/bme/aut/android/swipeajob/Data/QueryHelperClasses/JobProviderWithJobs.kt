package hu.bme.aut.android.swipeajob.Data.QueryHelperClasses

import androidx.room.Embedded
import androidx.room.Relation
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider


data class JobProviderWithJobs(
    @Embedded val jobProvider: JobProvider,
    @Relation(
        parentColumn = "jobproviderid",
        entityColumn = "job_owner_jobproviderid"
    )
    val jobs: List<Job>
)