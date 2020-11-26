package hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher

@Entity(tableName = "skillitem",
    foreignKeys = [
        ForeignKey(
            entity = JobSearcher::class,
            parentColumns = arrayOf("jobsearcherid"),
            childColumns = arrayOf("skill_owner_jobsearcherid"),
            onDelete = ForeignKey.CASCADE)]
)
data class SkillItem (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val skillId: Long?,
    @ColumnInfo(name = "skill_owner_jobsearcherid") var jobsearcherId: Long?,
    @ColumnInfo(name = "skillName") val skillName: String
)