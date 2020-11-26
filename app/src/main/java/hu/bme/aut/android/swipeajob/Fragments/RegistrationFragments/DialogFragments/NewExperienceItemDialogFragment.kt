package hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.swipeajob.Globals.Consts
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.R

class NewExperienceItemDialogFragment(private val listener: NewExperienceItemDialogListener): DialogFragment()
{
    interface NewExperienceItemDialogListener
    {
        fun onExperienceItemCreated(newItem: ExperienceItem)
    }

    private lateinit var companyNameEditText: EditText
    private lateinit var startingYearPicker: NumberPicker
    private lateinit var endingYearPicker: NumberPicker

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
        experienceId = null,
        jobsearcherId = null,
        startYear = startingYearPicker.value,
        endYear = endingYearPicker.value,
        companyName = companyNameEditText.text.toString()
    )

    private fun getContentView(): View
    {
        val contentView = LayoutInflater
            .from(context)
            .inflate(R.layout.dialog_new_experience_item, null)

        companyNameEditText = contentView.findViewById(R.id.companyNameEditText)

        startingYearPicker = contentView.findViewById(R.id.startingYearPicker)
        startingYearPicker.minValue = Consts.YEAR_PICKER_MIN_VALUE
        startingYearPicker.maxValue = Consts.YEAR_PICKER_MAX_VALUE
        startingYearPicker.value = Consts.YEAR_PICKER_INITIAL_VALUE
        startingYearPicker.wrapSelectorWheel = false

        endingYearPicker = contentView.findViewById(R.id.endingYearPicker)
        endingYearPicker.minValue = Consts.YEAR_PICKER_MIN_VALUE
        endingYearPicker.maxValue = Consts.YEAR_PICKER_MAX_VALUE
        endingYearPicker.value = Consts.YEAR_PICKER_INITIAL_VALUE
        endingYearPicker.wrapSelectorWheel = false

        return contentView
    }

    companion object {
        const val TAG = "NewExperienceItemDialogFragment"
    }
}