package hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ExperienceItem (

    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "startYear") val startYear: Int,
    @ColumnInfo(name = "endYear") val endYear: Int,
    @ColumnInfo(name = "companyName") val companyName: String
)