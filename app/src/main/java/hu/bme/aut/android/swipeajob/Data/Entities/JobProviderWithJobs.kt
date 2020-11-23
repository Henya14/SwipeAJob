package hu.bme.aut.android.swipeajob.Data.Entities

import androidx.room.Embedded
import androidx.room.Relation


data class JobProviderWithJobs(
    @Embedded val jobProvider: JobProvider,
    @Relation(
        parentColumn = "jobproviderid",
        entityColumn = "job_owner_jobproviderid"
    )
    val jobs: List<Job>
)