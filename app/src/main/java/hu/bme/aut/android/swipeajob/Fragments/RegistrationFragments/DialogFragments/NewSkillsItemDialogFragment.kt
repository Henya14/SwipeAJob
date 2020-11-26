package hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem
import hu.bme.aut.android.swipeajob.R

class NewSkillsItemDialogFragment(private val listener: NewSkillsItemDialogListener): DialogFragment()
{
    interface NewSkillsItemDialogListener
    {
        fun onSkillItemCreated(newItem: SkillItem)
    }

    private lateinit var skillNameEditText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_new_skill_item))
            .setView(getContentView())
            .setPositiveButton(getString(R.string.ok_text)){
                    dialogInterface, i ->
                if(isValid())
                {
                    listener.onSkillItemCreated(getSkillItem())
                }
            }
            .create()
    }

    private fun isValid() = skillNameEditText.text.isNotEmpty()

    private fun getSkillItem(): SkillItem = SkillItem(
        skillId = null,
        jobsearcherId = null,
        skillName = skillNameEditText.text.toString()
    )

    private fun getContentView(): View
    {
        val contentView = LayoutInflater
            .from(context)
            .inflate(R.layout.dialog_new_skill_item, null)

        skillNameEditText = contentView.findViewById(R.id.skillNameEditText)
        return contentView
    }

    companion object {
        const val TAG = "NewSkillItemDialogFragment"
    }
}