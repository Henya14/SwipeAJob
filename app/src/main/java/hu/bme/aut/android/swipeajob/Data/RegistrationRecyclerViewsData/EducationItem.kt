package hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher

@Entity(tableName = "educationitem",
    foreignKeys = [
        ForeignKey(
        entity = JobSearcher::class,
        parentColumns = arrayOf("jobsearcherid"),
        childColumns = arrayOf("education_owner_jobsearcherid"),
        onDelete = ForeignKey.CASCADE)]
)
data class EducationItem (

    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val educationId: Long?,
    @ColumnInfo(name = "education_owner_jobsearcherid") var jobsearcherId: Long?,
    @ColumnInfo(name = "graduationYear") val graduationYear: Int,
    @ColumnInfo(name = "schoolName") val schoolName: String
)
