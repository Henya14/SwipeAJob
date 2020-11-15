package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.*
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcherWithEmbeddedClasses

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
}