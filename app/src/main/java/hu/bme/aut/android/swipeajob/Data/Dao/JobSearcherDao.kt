package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.*
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcherWithEmbeddedClasses
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearchersThatSwipedJobs

@Dao
interface JobSearcherDao{

    @Query("SELECT * FROM jobsearcher")
    fun getAll(): List<JobSearcher>

    @Insert
    fun insert(jobsearcher: JobSearcher): Long

    @Delete
    fun delete(jobSearcher: JobSearcher)

    @Transaction
    @Query("SELECT * FROM jobsearcher")
    fun getJobSearcherWithEmbeddedClases() : List<JobSearcherWithEmbeddedClasses>

    @Query("SELECT * FROM jobsearcher WHERE user_name = :username")
    fun getAllJobSearchersWithUsername(username: String): List<JobSearcher>

    @Query("SELECT * FROM jobsearcher WHERE jobsearcherid = :id")
    fun getJobSearchersWithID(id: Long): JobSearcher

    @Transaction
    @Query("SELECT * FROM jobsearcher")
    fun getJobSearchersThatSwipedJobs(): List<JobSearchersThatSwipedJobs>
}