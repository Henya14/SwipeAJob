package hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobProvider.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.EducationRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.ExperienceRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.SkillsRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.R
import kotlin.concurrent.thread

class JobSearcherDetailsDialogFragment(val jobsearcher : JobSearcher): DialogFragment()
{

    private lateinit var educationRecyclerViewAdapter: EducationRecyclerViewAdapter
    private lateinit var experienceRecyclerViewAdapter: ExperienceRecyclerViewAdapter
    private lateinit var skillsRecyclerViewAdapter: SkillsRecyclerViewAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(jobsearcher.fullname)
            .setView(getContentView())
            .setPositiveButton(getString(R.string.ok_text))
            {_,_ -> }
            .create()
    }

    private fun getContentView(): View {

        val contentView = LayoutInflater
            .from(context)
            .inflate(R.layout.dialog_jobsearcherdetails, null)




        if(jobsearcher.pictureUri != null) {
            contentView.findViewById<ImageView>(R.id.jobsearcher_image)
                .setImageURI(Uri.parse(jobsearcher!!.pictureUri))
        }
        contentView.findViewById<TextView>(R.id.jobsearcher_FullName).text = jobsearcher.fullname
        contentView.findViewById<Button>(R.id.addEducationButton).visibility = View.INVISIBLE
        contentView.findViewById<Button>(R.id.addExperienceButton).visibility = View.INVISIBLE
        contentView.findViewById<Button>(R.id.addSkillButton).visibility = View.INVISIBLE

        val educationRecyclerView = contentView.findViewById<RecyclerView>(R.id.educationRecyclerView)
        val experienceRecyclerView = contentView.findViewById<RecyclerView>(R.id.experienceRecyclerView)
        val skillsRecyclerView = contentView.findViewById<RecyclerView>(R.id.skillsRecyclerView)

        educationRecyclerViewAdapter = EducationRecyclerViewAdapter(false)
        educationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        educationRecyclerView.adapter = educationRecyclerViewAdapter

        experienceRecyclerViewAdapter = ExperienceRecyclerViewAdapter(false)
        experienceRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        experienceRecyclerView.adapter = experienceRecyclerViewAdapter

        skillsRecyclerViewAdapter = SkillsRecyclerViewAdapter(false)
        skillsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        skillsRecyclerView.adapter = skillsRecyclerViewAdapter

        loadData()
        return contentView
    }

    private fun loadData() {
        thread {
            val jobSearcherWithEmbeddedClasses = AppDatabase.getInstance(requireContext()).jobsearcherDao().getJobSearcherWithEmbeddedClasesWithUserName(jobsearcher.userName)
            requireActivity().runOnUiThread{
                educationRecyclerViewAdapter.addItems(jobSearcherWithEmbeddedClasses?.educations ?: emptyList())
                experienceRecyclerViewAdapter.addItems(jobSearcherWithEmbeddedClasses?.experiences ?: emptyList())
                skillsRecyclerViewAdapter.addItems(jobSearcherWithEmbeddedClasses?.skills ?: emptyList())
            }

        }
    }


    companion object {
        const val TAG = "JobSearcherDetail"
    }
}