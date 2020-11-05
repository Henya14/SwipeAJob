package hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class EducationItem (

    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "graduationYear") val graduationYear: Int,
    @ColumnInfo(name = "schoolName") val schoolName: String
)
