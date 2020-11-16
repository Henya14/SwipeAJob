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
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.swipeajob.Data.Datebase.AppDatabase
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Fragments.MainActivityFragments.RegistrationFragmentDirections
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.fragment_job_provider_registration.*
import kotlinx.android.synthetic.main.registration_fragment_common_layout.*
import java.io.File
import kotlin.concurrent.thread

class JobProviderRegistrationFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_provider_registration, container, false)
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
    }


    fun registerButtonOnClick(view: View) {
        registerButton.isEnabled = false
        if(validateInput())
            registerNewJobProvider()
        registerButton.isEnabled = true
    }

    private fun registerNewJobProvider() {

        val jp = JobProvider(id = null,
        userName = userNameInputField.editText!!.text.toString(),
        password = passwordInputField.editText!!.text.toString(),
        phoneNumber = phoneNumberInput.number,
        companyname = comapnyNameInputField.editText!!.text.toString())

        thread {

            AppDatabase.getInstance(requireContext()).jobproviderDao().insert(jp)
            Snackbar.make(requireView(), getString(R.string.successfull_registeration), Snackbar.LENGTH_LONG).show()

            val direction = RegistrationFragmentDirections.actionRegistrationFragmentToMainFragment()
            findNavController().navigate(direction)
        }

    }

    private fun validateInput(): Boolean {

        userNameInputField.isErrorEnabled = false
        passwordInputField.isErrorEnabled = false

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

        if(comapnyNameInputField.editText!!.text!!.isEmpty())
        {
            comapnyNameInputField.requestFocus()
            comapnyNameInputField.error = getString(R.string.company_name_input_error)
            return false
        }


        return true
    }

    @Synchronized
    private fun isUserNameUnique(): Boolean {

        var unique = true

        val t = thread {
            val result = AppDatabase.getInstance(requireContext()).jobproviderDao().getAllJobProvidersWithUsername(userNameInputField.editText!!.text.toString())
            if(result.size > 0)
                unique = false

        }

        t.join()


        return unique

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
        const val PAGE_TITLE = "Job Provider"
    }



}