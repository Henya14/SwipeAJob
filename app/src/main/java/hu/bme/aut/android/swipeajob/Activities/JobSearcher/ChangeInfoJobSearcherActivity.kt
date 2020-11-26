package hu.bme.aut.android.swipeajob.Activities.JobSearcher

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.EducationRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.ExperienceRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.SkillsRecyclerViewAdapter
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments.NewEducationItemDialogFragment
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments.NewExperienceItemDialogFragment
import hu.bme.aut.android.swipeajob.Fragments.RegistrationFragments.DialogFragments.NewSkillsItemDialogFragment
import hu.bme.aut.android.swipeajob.Globals.Consts
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.activity_change_info_common_layout.passwordInputField
import kotlinx.android.synthetic.main.activity_change_info_common_layout.phoneNumberInput
import kotlinx.android.synthetic.main.activity_change_info_common_layout.uploadedImage
import kotlinx.android.synthetic.main.activity_change_info_jobsearcher.*
import kotlinx.android.synthetic.main.activity_change_info_jobsearcher.fullNameInputField
import kotlinx.android.synthetic.main.activity_change_info_jobsearcher.greyLoadingScreen
import kotlinx.android.synthetic.main.activity_change_info_jobsearcher.loadingProgressBar
import kotlinx.android.synthetic.main.activity_change_info_jobsearcher.okchangeButton
import kotlinx.android.synthetic.main.education_list_layout.*
import kotlinx.android.synthetic.main.experience_list_layout.*
import kotlinx.android.synthetic.main.skills_list_layout.*
import kotlin.concurrent.thread

class ChangeInfoJobSearcherActivity : AppCompatActivity(),
    NewEducationItemDialogFragment.NewEducationItemDialogListener,
    NewExperienceItemDialogFragment.NewExperienceItemDialogListener,
    NewSkillsItemDialogFragment.NewSkillsItemDialogListener,
    EducationRecyclerViewAdapter.EducationItemRemovedListener,
    ExperienceRecyclerViewAdapter.ExperienceItemRemovedListener,
    SkillsRecyclerViewAdapter.SkillItemRemovedListener
{

    companion object {
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }



    private lateinit var userName: String
    private lateinit var pictureUri: String
    private lateinit var password: String
    private lateinit var phoneNumber: String
    private lateinit var fullname: String

    private lateinit var educationRecyclerViewAdapter: EducationRecyclerViewAdapter
    private lateinit var experienceRecyclerViewAdapter: ExperienceRecyclerViewAdapter
    private lateinit var skillsRecyclerViewAdapter: SkillsRecyclerViewAdapter


    private val deletedEducationItems = mutableListOf<EducationItem>()
    private val newEducationItems = mutableListOf<EducationItem>()


    private val deletedExperienceItems = mutableListOf<ExperienceItem>()
    private val newExperienceItems = mutableListOf<ExperienceItem>()


    private val deletedSkillItems = mutableListOf<SkillItem>()
    private val newSkillItems = mutableListOf<SkillItem>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_info_jobsearcher)

        userName = this.intent.getStringExtra(KEY_USER_NAME)
            ?: throw Exception(getString(R.string.nousername_exception))

        greyLoadingScreen.visibility = View.VISIBLE
        loadingProgressBar.visibility = View.VISIBLE

        loadData()
        initAddButtonOnClickListeners()

        okchangeButton.setOnClickListener(::okChangeButtonClicked)
        cancel_change.setOnClickListener(::cancelChangeButtonClicked)

        uploadedImage.setOnClickListener{
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(Consts.IMAGE_PICKER_MAX_WIDTH, Consts.IMAGE_PICKER_MAX_HEIGHT)
                .start()
        }

    }



    private fun loadData() {

        thread {
            val jobsearcherWithEmbeddedclasses = AppDatabase.getInstance(this)
                .jobsearcherDao()
                .getJobSearcherWithEmbeddedClasesWithUserName(userName)

            runOnUiThread {
                pictureUri = jobsearcherWithEmbeddedclasses.jobSearcher.pictureUri
                uploadedImage.setImageURI(Uri.parse(pictureUri))
                uploadedImage.scaleType = ImageView.ScaleType.FIT_XY

                password = jobsearcherWithEmbeddedclasses.jobSearcher.password
                passwordInputField.editText!!.setText(password)

                phoneNumber = jobsearcherWithEmbeddedclasses.jobSearcher.phoneNumber
                phoneNumberInput.number = phoneNumber

                fullname = jobsearcherWithEmbeddedclasses.jobSearcher.fullname
                fullNameInputField.editText!!.setText(fullname)

                initRecyclerViews()



                educationRecyclerViewAdapter.addItems(jobsearcherWithEmbeddedclasses.educations)
                experienceRecyclerViewAdapter.addItems(jobsearcherWithEmbeddedclasses.experiences)
                skillsRecyclerViewAdapter.addItems(jobsearcherWithEmbeddedclasses.skills)



                greyLoadingScreen.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
            }

        }


    }

    private fun initAddButtonOnClickListeners() {
        addEducationButton.setOnClickListener{
            NewEducationItemDialogFragment(this)
                .show(this.supportFragmentManager, NewEducationItemDialogFragment.TAG) }

        addExperienceButton.setOnClickListener{ NewExperienceItemDialogFragment(this)
            .show(this.supportFragmentManager, NewExperienceItemDialogFragment.TAG)}

        addSkillButton.setOnClickListener { NewSkillsItemDialogFragment(this)
            .show(this.supportFragmentManager, NewSkillsItemDialogFragment.TAG)  }
    }

    private fun initRecyclerViews() {

        educationRecyclerViewAdapter = EducationRecyclerViewAdapter(educationItemRemovedListener = this)
        educationRecyclerView.layoutManager = LinearLayoutManager(this)
        educationRecyclerView.adapter = educationRecyclerViewAdapter

        experienceRecyclerViewAdapter = ExperienceRecyclerViewAdapter(experienceItemRemovedListener = this)
        experienceRecyclerView.layoutManager = LinearLayoutManager(this)
        experienceRecyclerView.adapter = experienceRecyclerViewAdapter

        skillsRecyclerViewAdapter = SkillsRecyclerViewAdapter(skillItemRemovedListener = this)
        skillsRecyclerView.layoutManager = LinearLayoutManager(this)
        skillsRecyclerView.adapter = skillsRecyclerViewAdapter
    }

    private fun okChangeButtonClicked(v: View) {
        okchangeButton.isEnabled = false
        cancel_change.isEnabled = false

        if (validateInput(v))
            updateJobSearcher()

        cancel_change.isEnabled = true
        okchangeButton.isEnabled = true
    }

    private fun validateInput(v: View): Boolean {

        passwordInputField.isErrorEnabled = false
        fullNameInputField.isErrorEnabled = false


        if(passwordInputField.editText!!.text!!.isEmpty())
        {
            passwordInputField.requestFocus()
            passwordInputField.error = getString(R.string.password_input_error)
            return false
        }

        if(!phoneNumberInput.isValid)
        {
            phoneNumberInput.requestFocus()
            Snackbar.make(v, getString(R.string.phoneNumberValidationText), Snackbar.LENGTH_LONG).show()
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

    private fun updateJobSearcher()
    {
        password = passwordInputField.editText!!.text.toString()
        phoneNumber = phoneNumberInput.number
        fullname = fullNameInputField.editText!!.text.toString()

        thread{
            val db = AppDatabase.getInstance(this)
            db.runInTransaction {
                db.jobsearcherDao()
                    .updateJobSearcherWithUserName(username = userName,
                        pictureUri = pictureUri,
                        password = password,
                        phoneNumber =   phoneNumber,
                        fullName = fullname)

                deletedEducationItems.forEach {
                    db.educationitemDao().delete(it)
                }

                deletedExperienceItems.forEach {
                    db.experienceitemDao().delete(it)
                }

                deletedSkillItems.forEach {
                    db.skillitemDao().delete(it)
                }

                val jobsearcherid = db.jobsearcherDao().getJobSearcherIdForUserName(userName)
                newEducationItems.forEach {
                    it.jobsearcherId = jobsearcherid
                    db.educationitemDao().insert(it)
                }

                newExperienceItems.forEach {
                    it.jobsearcherId = jobsearcherid
                    db.experienceitemDao().insert(it)
                }

                newSkillItems.forEach {
                    it.jobsearcherId = jobsearcherid
                    db.skillitemDao().insert(it)
                }

                runOnUiThread {
                    finish()
                }

            }


        }
    }

    private fun cancelChangeButtonClicked(v:View)
    {
        finish()
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

            ImagePicker.RESULT_ERROR -> Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()

            else -> Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onEducationItemCreated(newItem: EducationItem) {
        newEducationItems.add(newItem)
        educationRecyclerViewAdapter.addItem(newItem)
    }

    override fun onExperienceItemCreated(newItem: ExperienceItem) {
        newExperienceItems.add(newItem)
        experienceRecyclerViewAdapter.addItem(newItem)
    }

    override fun onSkillItemCreated(newItem: SkillItem) {
        newSkillItems.add(newItem)
        skillsRecyclerViewAdapter.addItem(newItem)
    }

    override fun educationItemRemoved(educationItem: EducationItem) {
        deletedEducationItems.add(educationItem)

    }

    override fun experienceItemRemoved(experienceItem: ExperienceItem) {
        deletedExperienceItems.add(experienceItem)

    }

    override fun skillItemRemoved(skillItem: SkillItem) {
        deletedSkillItems.add(skillItem)
    }

    //TODO destroy db
}