package hu.bme.aut.android.swipeajob.Data.Daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem

@Dao
interface SkillItemDao {

    @Query("SELECT * FROM skillitem")
    fun getAll(): List<SkillItem>

    @Insert
    fun insert(skillItem: SkillItem)

    @Delete
    fun delete(skillItem: SkillItem)
}