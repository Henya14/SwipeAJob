package hu.bme.aut.android.swipeajob.Data.Datebase

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.swipeajob.Data.Dao.*
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem

@Database(entities = arrayOf(JobSearcher::class, JobProvider::class, EducationItem::class, ExperienceItem::class, SkillItem::class), version = 1)
abstract class AppDatabase : RoomDatabase()
{

    abstract fun jobsearcherDao(): JobSearcherDao
    abstract fun jobproviderDao(): JobProviderDao

    abstract fun educationitemDao(): EducationItemDao
    abstract fun experienceitemDao(): ExperienceItemDao
    abstract fun skillitemDao(): SkillItemDao



}