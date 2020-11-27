package hu.bme.aut.android.swipeajob.Data.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "job",
    foreignKeys = [
    ForeignKey(
    entity = JobProvider::class,
    parentColumns = arrayOf("jobproviderid"),
    childColumns = arrayOf("job_owner_jobproviderid"),
    onDelete = ForeignKey.CASCADE)])
data class Job(
    @ColumnInfo(name = "jobid") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "job_owner_jobproviderid") var jobproviderId: Long?,
    @ColumnInfo(name = "pictureuri") var pictureUri: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "removed") var removed: Boolean = false
)