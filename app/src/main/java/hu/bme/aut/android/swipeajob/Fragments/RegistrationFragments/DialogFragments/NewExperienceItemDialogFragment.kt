package hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.R

class NewExperienceItemDialogFragment(val listener: NewExperienceItemDialogListener): DialogFragment()
{
    interface NewExperienceItemDialogListener
    {
        fun onExperienceItemCreated(newItem: ExperienceItem)
    }

    private lateinit var companyNameEditText: EditText
    private lateinit var startingYearPicker: DatePicker
    private lateinit var endingYearPicker: DatePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_new_experience_item))
            .setView(getContentView())
            .setPositiveButton(getString(R.string.ok_text)){
                dialogInterface, i ->
                if(isValid())
                {
                    listener.onExperienceItemCreated(getExperienceItem())
                }
            }
            .create()
    }

    private fun isValid() = companyNameEditText.text.isNotEmpty()

    private fun getExperienceItem(): ExperienceItem = ExperienceItem(
        null,
        startYear = startingYearPicker.year,
        endYear = endingYearPicker.year,
        companyName = companyNameEditText.text.toString()
    )

    private fun getContentView(): View
    {
        val contentView = LayoutInflater
            .from(context)
            .inflate(R.layout.dialog_new_experience_item, null)

        companyNameEditText = contentView.findViewById(R.id.companyNameEditText)
        startingYearPicker = contentView.findViewById(R.id.startingYearPicker)
        endingYearPicker = contentView.findViewById(R.id.endingYearPicker)
        return contentView
    }

    companion object {
        const val TAG = "NewExperienceItemDialogFragment"
    }
}