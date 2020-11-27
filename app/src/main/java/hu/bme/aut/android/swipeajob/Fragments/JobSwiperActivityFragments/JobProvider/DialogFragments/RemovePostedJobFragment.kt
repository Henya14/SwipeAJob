package hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobProvider.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ViewUtils.getContentView
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.JobsRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.R
import kotlin.concurrent.thread

class RemovePostedJobFragment(val username : String) : DialogFragment(),
    JobsRecyclerViewAdapter.JobItemRemovedListener {

    companion object {
        const val TAG = "RemovePostedJobDialogFragment"
    }

    private lateinit var jobsRecyclerViewAdapter: JobsRecyclerViewAdapter

    private lateinit var loadingProgressBar: ProgressBar

    private val removedJobs = mutableListOf<Job>()



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return  AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.remove_jobs))
            .setView(getContentView())
            .setPositiveButton(getString(R.string.ok_text)){
                _,_ ->
                RemoveRemovedJobs()
            }.setNegativeButton(getString(R.string.action_cancel)){
                _,_ ->
            }
            .create();

        return super.onCreateDialog(savedInstanceState)
    }

    private fun getContentView(): View {
        val contentView= LayoutInflater.from(context)
            .inflate(R.layout.dialog_edit_jobs,null)

        loadingProgressBar = contentView.findViewById(R.id.loadingProgressBar)
        loadingProgressBar.visibility = View.VISIBLE

        val jobsRecyclerView = contentView.findViewById<RecyclerView>(R.id.postedJobsRecyclerView)

        jobsRecyclerViewAdapter = JobsRecyclerViewAdapter(requireContext(),
            this)
        jobsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        jobsRecyclerView.adapter = jobsRecyclerViewAdapter

        loadjobs()

        return contentView
    }

    private fun loadjobs() {

        thread {
            val db = AppDatabase.getInstance(requireContext())

            val jobproviderWithJobs = db.jobproviderDao()
                .getJobProviderWithJobsWithUserName(username = username)

            requireActivity().runOnUiThread{
                jobsRecyclerViewAdapter.setItems(jobproviderWithJobs.jobs)
                loadingProgressBar.visibility = View.GONE
            }
        }
    }

    private fun RemoveRemovedJobs()
    {
        thread {
            val db = AppDatabase.getInstance(requireContext())
            db.runInTransaction {
                removedJobs.forEach {
                    db.jobDao().removeJob(it.id!!)
                }
            }
        }
    }

    override fun jobItemRemoved(jobItem: Job) {
        removedJobs.add(jobItem)
    }


}