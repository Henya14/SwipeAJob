package hu.bme.aut.android.swipeajob.Data.Daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobRightSwipeCrossRef

@Dao
interface JobSwiperJobRightSwipeCrossRefDao {

    @Insert
    fun insert(jobSwiperJobRightSwipeCrossRef: JobSwiperJobRightSwipeCrossRef)

    @Delete
    fun delete(jobSwiperJobRightSwipeCrossRef: JobSwiperJobRightSwipeCrossRef)
}