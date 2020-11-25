package hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobProvider.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.R

class NewJobDialogFragment(val listener: NewJobItemDialogListener,val pictureUri: String?): DialogFragment()
{

    interface NewJobItemDialogListener
    {
        fun onJobItemCreated(newJob: Job)
    }

    private lateinit var jobname: EditText
    private lateinit var jobdescription: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.post_new_job))
            .setView(getContentView())
            .setPositiveButton(getString(R.string.ok_text)){
                    _,_ ->
                if(isValid())
                {
                    listener.onJobItemCreated(getJobItem())
                }
            }.create()
    }


    private fun getContentView(): View {

        val contentView = LayoutInflater
            .from(context)
            .inflate(R.layout.dialog_new_job, null)
            jobname = contentView.findViewById(R.id.jobnameEditText)
            jobdescription = contentView.findViewById(R.id.jobdescriptionEditText)

        return contentView
    }

    private fun getJobItem(): Job = Job(
        id = null,
        jobproviderId = null,
        pictureUri = pictureUri,
        name = jobname.text.toString(),
        description = jobdescription.text.toString()
    )

    private fun isValid(): Boolean
    {
        if(jobname.text.isEmpty()) {
            jobname.error = getString(R.string.new_job_enter_name_error)
            return false
        }
        return true
    }
    companion object {
        const val TAG = "NewJobDialogFragment"
    }
}