package hu.bme.aut.android.swipeajob.Fragments.MainActivityFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dd.processbutton.iml.ActionProcessButton
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.swipeajob.Activities.JobProvider.JobSwiperActivityJobProvider
import hu.bme.aut.android.swipeajob.Activities.JobSearcher.JobSwiperActivityJobsearcher
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.Data.Entities.JobProvider
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.userNameInputField
import kotlin.concurrent.thread

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.app_name)
        loginButton.setMode(ActionProcessButton.Mode.PROGRESS)
        loginButton.progress = 0
        loginButton.setOnClickListener(::loginButtonOnClick)
        registerButton.setOnClickListener(::registerButtonOnClick)
        forgottenPasswordButton.setOnClickListener(::forgottenPasswordButtonOnClick)


    }


    private fun loginButtonOnClick(v: View) {


        registerButton.isEnabled = false
        forgottenPasswordButton.isEnabled = false
        userNameInputField.isEnabled = false
        loginButton.isEnabled = false
        passwordInputField.isEnabled = false
        loginButton.progress = 0

        if (validateInput()) {
            thread {

                while (loginButton.progress < 100) {
                    activity?.runOnUiThread {
                        loginButton.progress += 1
                    }
                    Thread.sleep(10)


                }
                var intent = Intent()
                if (userTypeForLoginToggle.isChecked) {

                    intent = Intent(requireActivity(), JobSwiperActivityJobsearcher::class.java)
                    intent.putExtra(
                        JobSwiperActivityJobsearcher.KEY_USER_NAME,
                        userNameInputField.editText!!.text.toString()
                    )
                } else {

                    intent = Intent(requireActivity(), JobSwiperActivityJobProvider::class.java)
                    intent.putExtra(
                        JobSwiperActivityJobProvider.KEY_USER_NAME,
                        userNameInputField.editText!!.text.toString()
                    )
                }



                startActivity(intent)

                requireActivity().finish()
            }

        } else {
            loginButton.progress = -1

            registerButton.isEnabled = true
            forgottenPasswordButton.isEnabled = true
            userNameInputField.isEnabled = true
            loginButton.isEnabled = true
            passwordInputField.isEnabled = true
        }


    }

    @Synchronized
    private fun validateInput(): Boolean {
        userNameInputField.isErrorEnabled = false
        passwordInputField.isErrorEnabled = false

        if (userNameInputField.editText!!.text.isEmpty()) {
            userNameInputField.requestFocus()
            userNameInputField.error = getString(R.string.username_input_error)

            return false
        }

        if (passwordInputField.editText!!.text.isEmpty()) {
            passwordInputField.requestFocus()
            passwordInputField.error = getString(R.string.password_login_input_error)
            return false
        }

        val userName = userNameInputField.editText!!.text.toString()

        if (userTypeForLoginToggle.isChecked) {
            var list: List<JobSearcher> = listOf()
            val t = thread {
                list = AppDatabase.getInstance(requireContext()).jobsearcherDao()
                    .getAllJobSearchersWithUsername(userName)
            }

            t.join()

            if (list.isEmpty()) {
                Snackbar.make(
                    requireView(),
                    requireContext().getString(R.string.jobsearcher_not_found_snackbar, userName),
                    Snackbar.LENGTH_LONG
                ).show()
                return false
            } else {
                if (list[0].password != passwordInputField.editText!!.text.toString()) {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.incorrect_password_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                    return false
                }
            }


        } else {

            var list: List<JobProvider> = listOf()

            val t = thread {
                list = AppDatabase.getInstance(requireContext()).jobproviderDao()
                    .getAllJobProvidersWithUsername(userName)
            }
            t.join()

            if (list.isEmpty()) {
                Snackbar.make(
                    requireView(),
                    requireContext().getString(R.string.jobprovider_not_found_snackbar, userName),
                    Snackbar.LENGTH_LONG
                ).show()
                return false
            } else {
                if (list[0].password != passwordInputField.editText!!.text.toString()) {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.incorrect_password_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                    return false
                }
            }


        }

        return true
    }


    private fun registerButtonOnClick(v: View) {
        loadingProgressBar.visibility = View.VISIBLE
        greyLoadingScreen.visibility = View.VISIBLE
        val action = MainFragmentDirections.actionMainFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }


    private fun forgottenPasswordButtonOnClick(v: View) {
        loadingProgressBar.visibility = View.VISIBLE
        greyLoadingScreen.visibility = View.VISIBLE
        val action = MainFragmentDirections.actionMainFragmentToForgottenPasswordFragment()
        findNavController().navigate(action)
    }

}