package hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment


import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.R
import java.lang.RuntimeException

class NewEducationItemDialogFragment(val listener: NewEducationItemDialogListener): DialogFragment()
{

    interface NewEducationItemDialogListener
    {
        fun onEducationItemCreated(newItem: EducationItem)
    }

    private lateinit var graduationYearDatePicker : DatePicker
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
        null,
        graduationYearDatePicker.year,
        nameEditText.text.toString()
    )
    private fun getContentView(): View {

        val contentView = LayoutInflater
            .from(context)
            .inflate(R.layout.dialog_new_education_item, null)
        graduationYearDatePicker = contentView.findViewById(R.id.graduationYearPicker)
        nameEditText = contentView.findViewById(R.id.schoolNameEditText)

        return contentView
    }

    companion object {
        const val TAG = "NewEducationItemDialogFragment"
    }
}