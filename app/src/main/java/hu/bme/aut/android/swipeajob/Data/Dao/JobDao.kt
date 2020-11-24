package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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


}