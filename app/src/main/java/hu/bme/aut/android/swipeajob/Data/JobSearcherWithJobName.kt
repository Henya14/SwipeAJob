package hu.bme.aut.android.swipeajob.Data

import hu.bme.aut.android.swipeajob.Activities.JobSwiperActivityJobsearcher
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher

data class JobSearcherWithJobName(
    val jobsearcher: JobSearcher,
    val jobName: String
)