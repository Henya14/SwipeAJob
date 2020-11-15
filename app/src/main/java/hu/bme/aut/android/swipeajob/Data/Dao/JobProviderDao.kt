package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher

@Dao
interface JobProviderDao{

    @Query("SELECT * FROM jobprovider")
    fun getAll(): List<JobProvider>

    @Insert
    fun insert(jobProvider: JobProvider)

    @Delete
    fun delete(jobProvider: JobProvider)
}