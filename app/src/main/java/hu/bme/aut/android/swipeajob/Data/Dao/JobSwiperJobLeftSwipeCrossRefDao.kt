package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobLeftSwipeCrossRef

@Dao
interface JobSwiperJobLeftSwipeCrossRefDao {

    @Insert
    fun insert(jobSwiperJobLeftSwipeCrossRef: JobSwiperJobLeftSwipeCrossRef)

    @Delete
    fun delete(jobSwiperJobLeftSwipeCrossRef: JobSwiperJobLeftSwipeCrossRef)
}