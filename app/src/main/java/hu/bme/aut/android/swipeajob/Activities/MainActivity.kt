package hu.bme.aut.android.swipeajob.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import hu.bme.aut.android.swipeajob.Data.Datebase.AppDatabase
import hu.bme.aut.android.swipeajob.Globals.Database
import hu.bme.aut.android.swipeajob.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Database.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "jobswiperdb"
        ).build()

    }


}