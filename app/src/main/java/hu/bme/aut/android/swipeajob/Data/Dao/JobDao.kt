package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearchersThatSwippedAJob

@Dao
interface JobDao{

        @Query("SELECT * FROM job")
        fun getAllJobs(): List<Job>
        @Transaction
        @Query("SELECT * FROM job")
        fun getJobSearchersThatSwippedAJob(): List<JobSearchersThatSwippedAJob>

        @Insert
        fun insert(job : Job): Long


}