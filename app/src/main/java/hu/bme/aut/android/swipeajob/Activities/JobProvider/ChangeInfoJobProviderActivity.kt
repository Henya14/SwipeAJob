package hu.bme.aut.android.swipeajob.Activities.JobProvider

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Globals.Consts
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.activity_change_info_common_layout.passwordInputField
import kotlinx.android.synthetic.main.activity_change_info_common_layout.phoneNumberInput
import kotlinx.android.synthetic.main.activity_change_info_common_layout.uploadedImage
import kotlinx.android.synthetic.main.activity_change_info_jobprovider.*
import kotlinx.android.synthetic.main.activity_change_info_jobprovider.comapnyNameInputField
import kotlin.concurrent.thread

class ChangeInfoJobProviderActivity : AppCompatActivity() {

    companion object {
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }

    private lateinit var userName: String
    private lateinit var pictureUri: String
    private lateinit var password: String
    private lateinit var phoneNumber: String
    private lateinit var companyName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_info_jobprovider)

        userName = this.intent.getStringExtra(KEY_USER_NAME)
            ?: throw Exception(getString(R.string.nousername_exception))


        greyLoadingScreen.visibility = View.VISIBLE
        loadingProgressBar.visibility = View.VISIBLE
        loadData()

        okchangeButton.setOnClickListener(::okChangeButtonClicked)
        cancelchangebutton.setOnClickListener(::cancelChangeButtonClicked)

        uploadedImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(Consts.IMAGE_PICKER_MAX_WIDTH,Consts.IMAGE_PICKER_MAX_HEIGHT)
                .start()
        }
    }

    private fun loadData() {


        thread {
            val jobprovider =
                AppDatabase.getInstance(this).jobproviderDao().getJobProviderForUsername(userName)
            runOnUiThread {
                pictureUri = jobprovider.pictureUri
                Glide.with(this).load(pictureUri).into(uploadedImage)
                uploadedImage.scaleType = ImageView.ScaleType.FIT_XY

                password = jobprovider.password
                passwordInputField.editText!!.setText(password)

                phoneNumber = jobprovider.phoneNumber
                phoneNumberInput.number = phoneNumber

                companyName = jobprovider.companyname
                comapnyNameInputField.editText!!.setText(companyName)

                greyLoadingScreen.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
            }
        }

    }

    private fun okChangeButtonClicked(v: View) {
        if (validateInput(v))
            updateJobProvider()

    }

    private fun validateInput(v: View): Boolean {

        passwordInputField.isErrorEnabled = false

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

        if(comapnyNameInputField.editText!!.text!!.isEmpty())
        {
            comapnyNameInputField.requestFocus()
            comapnyNameInputField.error = getString(R.string.company_name_input_error)
            return false
        }


        return true
    }

    private fun updateJobProvider()
    {
        password = passwordInputField.editText!!.text.toString()
        phoneNumber = phoneNumberInput.number
        companyName = comapnyNameInputField.editText!!.text.toString()

        thread {
            AppDatabase.getInstance(this).jobproviderDao()
                .updateJobProviderWithUsername(
                    username = userName,
                    pictureUri = pictureUri,
                    password = password,
                    phoneNumber = phoneNumber,
                    companyName = companyName
                )

            runOnUiThread {
                finish()
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

    override fun onResume() {
        AppDatabase.getInstance(this)
        super.onResume()
    }

    override fun onPause() {
        AppDatabase.destroyInstance()
        super.onPause()
    }


}