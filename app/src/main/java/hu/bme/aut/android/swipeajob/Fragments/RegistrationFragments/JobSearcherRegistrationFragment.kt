package hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.EducationRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.ExperienceRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.SkillsRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments.NewEducationItemDialogFragment
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments.NewExperienceItemDialogFragment
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments.NewSkillsItemDialogFragment
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.education_list_layout.*
import kotlinx.android.synthetic.main.experience_list_layout.*
import kotlinx.android.synthetic.main.fragment_job_searcher_registration.*
import kotlinx.android.synthetic.main.registration_fragment_common_layout.*
import kotlinx.android.synthetic.main.skills_list_layout.*
import java.io.File


class JobSearcherRegistrationFragment : Fragment(),
    NewEducationItemDialogFragment.NewEducationItemDialogListener,
    NewExperienceItemDialogFragment.NewExperienceItemDialogListener,
    NewSkillsItemDialogFragment.NewSkillsItemDialogListener{


    private lateinit var educationRecyclerViewAdapter: EducationRecyclerViewAdapter
    private lateinit var experienceRecyclerViewAdapter: ExperienceRecyclerViewAdapter
    private lateinit var skillsRecyclerViewAdapter: SkillsRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_searcher_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadedImage.setOnClickListener{
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }



        registerButton.setOnClickListener(::registerButtonOnClick)
        initAddButtonOnClickListeners()
        initRecyclerViews()



    }

    private fun initAddButtonOnClickListeners() {
        addEducationButton.setOnClickListener { NewEducationItemDialogFragment(this).show(requireActivity().supportFragmentManager, NewEducationItemDialogFragment.TAG) }
        addExperienceButton.setOnClickListener { NewExperienceItemDialogFragment(this).show(requireActivity().supportFragmentManager, NewExperienceItemDialogFragment.TAG) }
        addSkillButton.setOnClickListener{NewSkillsItemDialogFragment(this).show(requireActivity().supportFragmentManager, NewSkillsItemDialogFragment.TAG)}
    }

    private fun initRecyclerViews() {
        educationRecyclerViewAdapter = EducationRecyclerViewAdapter()
        educationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        educationRecyclerView.adapter = educationRecyclerViewAdapter

        experienceRecyclerViewAdapter = ExperienceRecyclerViewAdapter()
        experienceRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        experienceRecyclerView.adapter = experienceRecyclerViewAdapter

        skillsRecyclerViewAdapter = SkillsRecyclerViewAdapter()
        skillsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        skillsRecyclerView.adapter = skillsRecyclerViewAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            uploadedImage.setImageURI(fileUri)

            uploadedImage.scaleType = ImageView.ScaleType.FIT_XY

            //You can get File object from intent
            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath:String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val PAGE_TITLE = "Job Searcher"
    }

    override fun onEducationItemCreated(newItem: EducationItem) {

        educationRecyclerViewAdapter.addItem(newItem)
    }

    override fun onExperienceItemCreated(newItem: ExperienceItem) {
        experienceRecyclerViewAdapter.addItem(newItem)
    }

    override fun onSkillItemCreated(newItem: SkillItem) {
       skillsRecyclerViewAdapter.addItem(newItem)
    }


    fun registerButtonOnClick(v : View)
    {
        if(validateInput())
            registerNewJobSearcher()
    }

    private fun registerNewJobSearcher() {
        TODO("Not yet implemented")
    }

    private fun validateInput(): Boolean
    {

        if(userNameInputField.editText!!.text!!.isEmpty()) {
            userNameInputField.requestFocus()
            userNameInputField.error = getString(R.string.username_input_error)
            return false
        }

        else if(passwordInputField.editText!!.text!!.isEmpty())
        {
            passwordInputField.requestFocus()
            passwordInputField.error = getString(R.string.password_input_error)
            return false
        }

        else if(!phoneNumberInput.isValid)
        {
            phoneNumberInput.requestFocus()
            Snackbar.make(requireView(), getString(R.string.phoneNumberValidationText), Snackbar.LENGTH_LONG).show()
            return false
        }

        else if(fullNameInputField.editText!!.text!!.isEmpty())
        {
            fullNameInputField.requestFocus()
            fullNameInputField.error = getString(R.string.full_name_input_error)
            return false
        }
        return true
    }

}