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


import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.R

class NewEducationItemDialogFragment(val listener: NewEducationItemDialogListener): DialogFragment()
{

    interface NewEducationItemDialogListener
    {
        fun onEducationItemCreated(newItem: EducationItem)
    }

    private lateinit var graduationYear : NumberPicker
    private lateinit var nameEditText : EditText




    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_new_education_item))
            .setView(getContentView())
            .setPositiveButton(getString(R.string.ok_text)){
                dialogInterface, i ->
                if(isValid())
                {
                    listener.onEducationItemCreated(getEducationItem())
                }
            }
            .create()
    }



    private fun isValid() = nameEditText.text.isNotEmpty()

    private fun getEducationItem(): EducationItem = EducationItem(
        educationId = null,
        jobsearcherId = null,
        graduationYear = graduationYear.value,
        schoolName = nameEditText.text.toString()
    )
    private fun getContentView(): View {

        val contentView = LayoutInflater
            .from(context)
            .inflate(R.layout.dialog_new_education_item, null)
        graduationYear = contentView.findViewById(R.id.graduationYearPicker)
        graduationYear.minValue = Consts.YEAR_PICKER_MIN_VALUE
        graduationYear.maxValue = Consts.YEAR_PICKER_MAX_VALUE
        graduationYear.value = Consts.YEAR_PICKER_INITIAL_VALUE
        graduationYear.wrapSelectorWheel = false

        nameEditText = contentView.findViewById(R.id.schoolNameEditText)

        return contentView
    }

    companion object {
        const val TAG = "NewEducationItemDialogFragment"
    }
}