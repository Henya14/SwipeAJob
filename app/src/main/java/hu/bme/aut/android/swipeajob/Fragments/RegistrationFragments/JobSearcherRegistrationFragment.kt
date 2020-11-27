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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.EducationRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.ExperienceRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.SkillsRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem
import hu.bme.aut.android.swipeajob.Fragments.MainActivityFragments.RegistrationFragmentDirections
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments.NewEducationItemDialogFragment
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments.NewExperienceItemDialogFragment
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments.NewSkillsItemDialogFragment
import hu.bme.aut.android.swipeajob.Globals.Consts
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.education_list_layout.*
import kotlinx.android.synthetic.main.experience_list_layout.*
import kotlinx.android.synthetic.main.registration_fragment_common_layout.*
import kotlinx.android.synthetic.main.registration_fragment_common_layout.passwordInputField
import kotlinx.android.synthetic.main.registration_fragment_common_layout.phoneNumberInput
import kotlinx.android.synthetic.main.registration_fragment_common_layout.uploadedImage
import kotlinx.android.synthetic.main.registration_fragment_job_searcher.*
import kotlinx.android.synthetic.main.skills_list_layout.*
import kotlin.concurrent.thread


class JobSearcherRegistrationFragment : Fragment(),
    NewEducationItemDialogFragment.NewEducationItemDialogListener,
    NewExperienceItemDialogFragment.NewExperienceItemDialogListener,
    NewSkillsItemDialogFragment.NewSkillsItemDialogListener{

    var pictureUri: String? = null
    private lateinit var educationRecyclerViewAdapter: EducationRecyclerViewAdapter
    private lateinit var experienceRecyclerViewAdapter: ExperienceRecyclerViewAdapter
    private lateinit var skillsRecyclerViewAdapter: SkillsRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.registration_fragment_job_searcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadedImage.setOnClickListener{
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(Consts.IMAGE_PICKER_MAX_WIDTH, Consts.IMAGE_PICKER_MAX_HEIGHT)
                .start()
        }



        registerButton.setOnClickListener(::registerButtonOnClick)
        initAddButtonOnClickListeners()
        initRecyclerViews()



    }

    private fun initAddButtonOnClickListeners() {
        addEducationButton.setOnClickListener { NewEducationItemDialogFragment(this)
            .show(requireActivity().supportFragmentManager, NewEducationItemDialogFragment.TAG) }

        addExperienceButton.setOnClickListener { NewExperienceItemDialogFragment(this)
            .show(requireActivity().supportFragmentManager, NewExperienceItemDialogFragment.TAG) }

        addSkillButton.setOnClickListener{NewSkillsItemDialogFragment(this)
            .show(requireActivity().supportFragmentManager, NewSkillsItemDialogFragment.TAG)}

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

        when(resultCode)
        {
            Activity.RESULT_OK -> {

                val fileUri = data?.data
                pictureUri = fileUri.toString()
                uploadedImage.setImageURI(fileUri)
                uploadedImage.scaleType = ImageView.ScaleType.FIT_XY

            }

            ImagePicker.RESULT_ERROR -> Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()

            else -> Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
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


    private fun registerButtonOnClick(v : View)
    {
        registerButton.isEnabled = false
        if(validateInput())
            registerNewJobSearcher()
        registerButton.isEnabled = true
    }

    private fun validateInput(): Boolean
    {

        userNameInputField.isErrorEnabled = false
        passwordInputField.isErrorEnabled = false
        fullNameInputField.isErrorEnabled = false

        if(userNameInputField.editText!!.text!!.isEmpty()) {
            userNameInputField.requestFocus()
            userNameInputField.error = getString(R.string.username_input_error)
            return false
        }

        if(!isUserNameUnique())
        {
            userNameInputField.requestFocus()
            userNameInputField.error = getString(R.string.usernametaken)
            return false
        }


        if(passwordInputField.editText!!.text!!.isEmpty())
        {
            passwordInputField.requestFocus()
            passwordInputField.error = getString(R.string.password_input_error)
            return false
        }

        if(!phoneNumberInput.isValid)
        {
            phoneNumberInput.requestFocus()
            Snackbar.make(requireView(), getString(R.string.phoneNumberValidationText), Snackbar.LENGTH_LONG).show()
            return false
        }

        if(fullNameInputField.editText!!.text!!.isEmpty())
        {
            fullNameInputField.requestFocus()
            fullNameInputField.error = getString(R.string.full_name_input_error)
            return false
        }
        return true
    }

    private fun registerNewJobSearcher() {

        val js  = JobSearcher(
            jobsearcherId = null,
            pictureUri = pictureUri?: "",
            userName = userNameInputField.editText!!.text.toString(),
            password = passwordInputField.editText!!.text.toString(),
            phoneNumber = phoneNumberInput.number,
            fullname = fullNameInputField.editText!!.text.toString()
        )
        thread {

            val db = AppDatabase.getInstance(requireContext())
            db.runInTransaction {

                val jobsearcherId =  AppDatabase.getInstance(requireContext()).jobsearcherDao().insert(js)

                educationRecyclerViewAdapter.items.forEach {
                    it.jobsearcherId = jobsearcherId
                    db.educationitemDao().insert(it)
                }

                experienceRecyclerViewAdapter.items.forEach {
                    it.jobsearcherId = jobsearcherId
                    db.experienceitemDao().insert(it)
                }

                skillsRecyclerViewAdapter.items.forEach {
                    it.jobsearcherId = jobsearcherId
                    db.skillitemDao().insert(it)
                }

            }


            Snackbar.make(requireView(), getString(R.string.successfull_registeration), Snackbar.LENGTH_LONG).show()

            val direction = RegistrationFragmentDirections.actionRegistrationFragmentToMainFragment()
            findNavController().navigate(direction)
        }
    }



    @Synchronized
    private fun isUserNameUnique(): Boolean {

        var unique = true

        val t = thread {
                val result = AppDatabase.getInstance(requireContext()).jobsearcherDao().getAllJobSearchersWithUsername(userNameInputField.editText!!.text.toString())
                if(result.isNotEmpty())
                    unique = false

        }

        t.join()


        return unique

    }

}