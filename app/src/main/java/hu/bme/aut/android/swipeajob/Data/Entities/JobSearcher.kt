package hu.bme.aut.android.swipeajob.Data.Entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
//TODO kép tárolása
@Entity(tableName = "jobsearcher")
data class JobSearcher(
    @ColumnInfo(name = "jobsearcherid") @PrimaryKey(autoGenerate = true) val jobsearcherId: Long?,
    @ColumnInfo(name = "pictureuri") val pictureUri: String?,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "fullname") val fullname: String
)