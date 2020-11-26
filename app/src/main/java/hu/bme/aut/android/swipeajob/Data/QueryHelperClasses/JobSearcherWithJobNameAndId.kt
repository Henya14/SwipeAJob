package hu.bme.aut.android.swipeajob.Data.QueryHelperClasses

import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher

data class JobSearcherWithJobNameAndId(
    val jobsearcher: JobSearcher,
    val jobName: String,
    val jobid: Long
)