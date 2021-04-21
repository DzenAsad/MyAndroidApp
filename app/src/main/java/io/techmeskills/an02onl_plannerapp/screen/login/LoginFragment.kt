package io.techmeskills.an02onl_plannerapp.screen.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.model.User
import io.techmeskills.an02onl_plannerapp.databinding.FragmentLoginBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : NavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override val viewBinding: FragmentLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.errorLiveData.observe(this.viewLifecycleOwner) { errorText ->
            Toast.makeText(requireContext(), errorText, Toast.LENGTH_SHORT).show()
        }

        viewModel.loggedIn.observe(this.viewLifecycleOwner) { loggedIn ->
            if (loggedIn) {
                findNavController().navigateSafe(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            } else {

                viewBinding.root.alpha = 1f
            }
        }

        viewBinding.buttonGO.setOnClickListener {
            val fn = viewBinding.firstName.text.toString()
            val ln = viewBinding.lastName.text.toString()
            viewModel.login(fn, ln)
        }


        viewBinding.buttonClear.setOnClickListener {
            viewBinding.firstName.setText("")
            viewBinding.lastName.setText("")
            viewModel.clear()
        }
    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
    }
}