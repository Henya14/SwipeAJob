package hu.bme.aut.android.swipeajob.Fragments.JobSwiperActivityFragments.JobProvider.DialogFragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.github.dhaval2404.imagepicker.ImagePicker
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Globals.Consts
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.registration_fragment_common_layout.*

class NewJobDialogFragment(val listener: NewJobItemDialogListener): DialogFragment()
{

    interface NewJobItemDialogListener
    {
        fun onJobItemCreated(newJob: Job)
    }


    private var pictureUri: String = ""
    private lateinit var jobUploadImageButton: ImageButton
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
            jobUploadImageButton = contentView.findViewById<ImageButton>(R.id.jobUploadImageButton)
            jobUploadImageButton.setOnClickListener {
                ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(Consts.IMAGE_PICKER_MAX_WIDTH, Consts.IMAGE_PICKER_MAX_HEIGHT)
                    .start()
            }
            jobname = contentView.findViewById(R.id.jobnameEditText)
            jobdescription = contentView.findViewById(R.id.jobdescriptionEditText)

        return contentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            pictureUri = fileUri.toString()
            jobUploadImageButton.setImageURI(fileUri)
            jobUploadImageButton.scaleType = ImageView.ScaleType.FIT_XY

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
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