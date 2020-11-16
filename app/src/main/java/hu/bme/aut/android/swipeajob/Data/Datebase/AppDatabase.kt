package hu.bme.aut.android.swipeajob.Data.Datebase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.android.swipeajob.Data.Dao.*
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem

@Database(entities = arrayOf(JobSearcher::class, JobProvider::class, EducationItem::class, ExperienceItem::class, SkillItem::class),
    version = 2)
abstract class AppDatabase : RoomDatabase()
{

    abstract fun jobsearcherDao(): JobSearcherDao
    abstract fun jobproviderDao(): JobProviderDao

    abstract fun educationitemDao(): EducationItemDao
    abstract fun experienceitemDao(): ExperienceItemDao
    abstract fun skillitemDao(): SkillItemDao


    companion object{
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context :Context): AppDatabase
        {
            if(INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "jobswiperdb").build()
            }

            return INSTANCE!!
        }

        fun destroyInstance()
        {
            INSTANCE = null
        }
    }

}