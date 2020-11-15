package hu.bme.aut.android.swipeajob.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Database
import androidx.room.Room
import hu.bme.aut.android.swipeajob.Data.Datebase.AppDatabase
import hu.bme.aut.android.swipeajob.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        AppDatabase.getInstance(this)
        super.onResume()
    }

    override fun onPause() {
        AppDatabase.destroyInstance()
        super.onPause()
    }
}