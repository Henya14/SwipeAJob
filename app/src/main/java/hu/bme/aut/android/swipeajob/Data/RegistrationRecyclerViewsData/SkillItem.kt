package hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class SkillItem (

    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "skillName") val skillName: String
)