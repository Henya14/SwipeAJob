package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobRightSwipeCrossRef

@Dao
interface JobSwiperJobRightSwipeCrossRefDao {

    @Insert
    fun insert(jobSwiperJobRightSwipeCrossRef: JobSwiperJobRightSwipeCrossRef): Long

    @Delete
    fun delete(jobSwiperJobRightSwipeCrossRef: JobSwiperJobRightSwipeCrossRef)
}