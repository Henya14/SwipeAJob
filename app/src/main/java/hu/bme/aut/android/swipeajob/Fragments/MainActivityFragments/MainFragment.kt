package hu.bme.aut.android.swipeajob.Fragments.MainActivityFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dd.processbutton.iml.ActionProcessButton
import hu.bme.aut.android.swipeajob.Activities.JobSwiperActivity
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.concurrent.thread

class MainFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false);
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


    fun loginButtonOnClick(v: View) {


            registerButton.isEnabled = false
            forgottenPasswordButton.isEnabled = false
            userNameInputField.isEnabled = false
            loginButton.isEnabled = false
            password_EditText.isEnabled = false


        thread {
            while (loginButton.progress < 100) {
                activity?.runOnUiThread {
                    loginButton.progress += 1
                }
                Thread.sleep(10)

            }



            val intent = Intent(requireActivity(), JobSwiperActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }


    }

    fun registerButtonOnClick(v: View) {
        loadingProgressBar.visibility = View.VISIBLE
        greyLoadingScreen.visibility = View.VISIBLE
        val action = MainFragmentDirections.actionMainFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }


    fun forgottenPasswordButtonOnClick(v: View)
    {
        val action = MainFragmentDirections.actionMainFragmentToForgottenPasswordFragment()
        findNavController().navigate(action)
    }

}