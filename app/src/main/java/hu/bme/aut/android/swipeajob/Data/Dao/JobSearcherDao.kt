package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.*
import hu.bme.aut.android.swipeajob.Data.Entities.*
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.JobSearcherWithEmbeddedClasses
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.JobSearchersThatSwipedJobs
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.MatchesForJobSearcher

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

    @Transaction
    @Query("SELECT * FROM jobsearcher WHERE jobsearcherid = :id")
    fun getMatchedJobProvidersForJobSearcherWithId(id: Long): MatchesForJobSearcher

    @Transaction
    @Query("SELECT * FROM jobsearcher WHERE user_name = :username")
    fun getMatchedJobProvidersForJobSearcherWithUsername(username: String): MatchesForJobSearcher
}