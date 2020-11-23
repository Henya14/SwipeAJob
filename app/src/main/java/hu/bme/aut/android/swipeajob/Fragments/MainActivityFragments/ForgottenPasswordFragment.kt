package hu.bme.aut.android.swipeajob.Fragments.MainActivityFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.swipeajob.Data.Database.AppDatabase
import hu.bme.aut.android.swipeajob.R
import kotlinx.android.synthetic.main.fragment_forgotten_password.*
import kotlin.concurrent.thread


class ForgottenPasswordFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgotten_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.forgotten_password_fragment_title)
        submitButton.setOnClickListener(::submitButtonOnClick)
    }

    private fun submitButtonOnClick(view: View?) {

        userNameInputField.isErrorEnabled = false
        if(userNameInputField.editText!!.text.isEmpty()) {
            userNameInputField.error = getString(R.string.username_cannot_be_empty)
            return
        }
        submitButton.isEnabled = false
            thread {
                val userName = userNameInputField.editText!!.text.toString()
                if(userTypeToggle.isChecked)
                {
                    val list = AppDatabase.getInstance(requireContext()).jobsearcherDao().getAllJobSearchersWithUsername(userName)

                    if(list.size > 0)
                        Snackbar.make(requireView(), "The Password For ${userName} is ${list[0].password}", Snackbar.LENGTH_LONG).show()
                    else
                        Snackbar.make(requireView(), "No Jobsearcher Is Registered With The Username ${userName}", Snackbar.LENGTH_LONG).show()
                }
                else
                {

                    val list = AppDatabase.getInstance(requireContext()).jobproviderDao().getAllJobProvidersWithUsername(userName)

                    if(list.size > 0)
                        Snackbar.make(requireView(), "The Password For ${userName} is ${list[0].password}", Snackbar.LENGTH_LONG).show()
                    else
                        Snackbar.make(requireView(), "No Jobprovider Is Registered With The Username ${userName}", Snackbar.LENGTH_LONG).show()

                }
            }

        submitButton.isEnabled = true
    }
}