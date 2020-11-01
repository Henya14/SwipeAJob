package hu.bme.aut.android.swipeajob.Fragments.MainActivityFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dd.processbutton.iml.ActionProcessButton

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
        loginButton.setMode(ActionProcessButton.Mode.PROGRESS)
        loginButton.progress = 0
        loginButton.setOnClickListener(::loginButtonOnClick)
        registerButton.setOnClickListener(::registerButtonOnClick)
        forgottenPasswordButton.setOnClickListener(::forgottenPasswordButtonOnClick)


    }


    fun loginButtonOnClick(v: View) {
        registerButton.isEnabled = false;
        loginButton.isEnabled = false
        forgottenPasswordButton.isEnabled = false;
        thread {
            while (loginButton.progress < 100) {
                activity?.runOnUiThread {
                    loginButton.progress += 1
                }
                Thread.sleep(10)

            }
            val action = MainFragmentDirections.actionMainFragmentToJobSwiperActivity()
            findNavController().navigate(action)
            activity?.runOnUiThread {
                registerButton.isEnabled = true;
                loginButton.isEnabled = true
                forgottenPasswordButton.isEnabled = true;
            }
        }


    }

    fun registerButtonOnClick(v: View) {
        val action = MainFragmentDirections.actionMainFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }


    fun forgottenPasswordButtonOnClick(v: View)
    {
        val action = MainFragmentDirections.actionMainFragmentToForgottenPasswordFragment()
        findNavController().navigate(action)
    }

}