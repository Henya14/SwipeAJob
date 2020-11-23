package hu.bme.aut.android.swipeajob.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobLeftSwipeCrossRef
import hu.bme.aut.android.swipeajob.Data.CrossReferences.JobSwiperJobRightSwipeCrossRef
import hu.bme.aut.android.swipeajob.Data.Dao.*
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem

@Database(entities = arrayOf(JobSearcher::class,
    JobProvider::class,
    EducationItem::class,
    ExperienceItem::class,
    SkillItem::class,
    Job::class,
    JobSwiperJobRightSwipeCrossRef::class,
    JobSwiperJobLeftSwipeCrossRef::class),
    version = 4,
    )
abstract class AppDatabase : RoomDatabase()
{

    abstract fun jobsearcherDao(): JobSearcherDao
    abstract fun jobproviderDao(): JobProviderDao

    abstract fun educationitemDao(): EducationItemDao
    abstract fun experienceitemDao(): ExperienceItemDao
    abstract fun skillitemDao(): SkillItemDao
    abstract fun jobDao(): JobDao

    abstract fun jobswiperJobRightSwipeCrossRefDao(): JobSwiperJobRightSwipeCrossRefDao
    abstract fun jobswiperJobLeftSwipeCrossRefDao(): JobSwiperJobLeftSwipeCrossRefDao



    companion object{
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context :Context): AppDatabase
        {
            if(INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "jobswiperdb").fallbackToDestructiveMigration().build()


            }

            return INSTANCE!!
        }

        fun destroyInstance()
        {
            INSTANCE = null
        }
    }
    

}