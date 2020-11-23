package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.*
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Data.Entities.JobProviderWithJobs

@Dao
interface JobProviderDao{

    @Query("SELECT * FROM jobprovider")
    fun getAll(): List<JobProvider>

    @Insert
    fun insert(jobProvider: JobProvider)

    @Delete
    fun delete(jobProvider: JobProvider)

    @Query("SELECT * FROM jobprovider WHERE user_name = :username")
    fun getAllJobProvidersWithUsername(username: String): List<JobProvider>

    @Transaction
    @Query("SELECT * FROM jobprovider")
    fun getJobProviderWithJobs(): List<JobProviderWithJobs>


    @Query("SELECT * FROM jobprovider WHERE user_name = :username")
    fun getJobProviderIdForUsername(username: String): JobProvider
}