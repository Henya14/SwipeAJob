package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem

@Dao
interface EducationItemDao {

    @Query("SELECT * FROM educationitem")
    fun getAll(): List<EducationItem>

    @Insert
    fun insert(educationItem: EducationItem)

    @Delete
    fun delete(educationItem: EducationItem)
}