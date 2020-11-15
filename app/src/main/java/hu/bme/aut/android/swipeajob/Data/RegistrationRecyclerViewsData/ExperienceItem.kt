package hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher

@Entity(tableName = "experienceitem",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = JobSearcher::class,
            parentColumns = arrayOf("jobsearcherid"),
            childColumns = arrayOf("experience_owner_jobsearcherid"),
            onDelete = ForeignKey.CASCADE
        ))
)
data class ExperienceItem (

    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val experienceId: Long?,
    @ColumnInfo(name = "experience_owner_jobsearcherid") var jobsearcherId: Long?,
    @ColumnInfo(name = "startYear") val startYear: Int,
    @ColumnInfo(name = "endYear") val endYear: Int,
    @ColumnInfo(name = "companyName") val companyName: String
)