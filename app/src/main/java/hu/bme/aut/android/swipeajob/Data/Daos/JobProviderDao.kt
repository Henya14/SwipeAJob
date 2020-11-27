package hu.bme.aut.android.swipeajob.Data.Daos

import androidx.room.*
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.JobProviderWithJobs
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.SwipedJobSearchersForJobProvider

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

    @Transaction
    @Query("SELECT * FROM jobprovider WHERE user_name = :username")
    fun getJobProviderWithJobsWithUserName(username: String): JobProviderWithJobs

    @Query("SELECT * FROM jobprovider WHERE user_name = :username")
    fun getJobProviderForUsername(username: String): JobProvider

    @Query("SELECT jobproviderid FROM jobprovider WHERE user_name = :username")
    fun getJobProviderIdForUsername(username: String): Long


    @Transaction
    @Query("SELECT * FROM jobprovider WHERE user_name = :username")
    fun getSwipedJobSearchersForJobProviderWithUsername(username: String): SwipedJobSearchersForJobProvider

    @Query("UPDATE jobprovider SET pictureuri = :pictureUri, password = :password, phone_number = :phoneNumber,  companyname = :companyName WHERE user_name = :username")
    fun updateJobProviderWithUsername(username: String, pictureUri: String, password: String, phoneNumber: String, companyName: String)
}