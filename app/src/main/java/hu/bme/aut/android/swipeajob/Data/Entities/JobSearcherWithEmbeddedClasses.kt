package hu.bme.aut.android.swipeajob.Data.Entities

import androidx.room.Embedded
import androidx.room.Relation
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem

data class JobSearcherWithEmbeddedClasses (
    @Embedded val jobSearcher: JobSearcher,
    @Relation(
        parentColumn = "jobsearcherid",
        entityColumn = "education_owner_jobsearcherid",
    )
    val educations: List<EducationItem>,

    @Relation(
        parentColumn = "jobsearcherid",
       entityColumn = "experience_owner_jobsearcherid"
    )
    val experiences: List<ExperienceItem>,

    @Relation(
        parentColumn = "jobsearcherid",
        entityColumn = "skill_owner_jobsearcherid"
    )
    val skills: List<SkillItem>
)