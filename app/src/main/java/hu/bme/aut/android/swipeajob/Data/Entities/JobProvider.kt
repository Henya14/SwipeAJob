package hu.bme.aut.android.swipeajob.Data.Entities

import android.graphics.Picture
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobprovider")
data class JobProvider(
    @ColumnInfo(name = "jobproviderid") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "pictureuri") val pictureUri: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "companyname") val companyname: String
)