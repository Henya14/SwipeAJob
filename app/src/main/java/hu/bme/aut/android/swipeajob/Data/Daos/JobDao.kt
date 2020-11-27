package hu.bme.aut.android.swipeajob.Data.Daos

import androidx.room.*
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.JobsThatWereSwiped

@Dao
interface JobDao{

        @Query("SELECT * FROM job")
        fun getAllJobs(): List<Job>

        @Transaction
        @Query("SELECT * FROM job")
        fun getJobsThatWereSwipped(): List<JobsThatWereSwiped>

        @Insert
        fun insert(job : Job): Long

        @Query("UPDATE job SET removed = 1 WHERE jobid = :id")
        fun removeJob(id : Long)

        @Delete
        fun delete(job: Job)


}