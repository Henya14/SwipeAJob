package hu.bme.aut.android.swipeajob.Data.Daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem

@Dao
interface ExperienceItemDao {

    @Query("SELECT * FROM experienceitem")
    fun getAll(): List<ExperienceItem>

    @Insert
    fun insert(experienceItem: ExperienceItem)

    @Delete
    fun delete(experienceItem: ExperienceItem)
}