package io.techmeskills.an02onl_plannerapp.screen.login

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.User
import io.techmeskills.an02onl_plannerapp.databinding.FragmentLoginBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : NavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override val viewBinding: FragmentLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getUser()?.let {
            findNavController().navigateSafe(
                LoginFragmentDirections.actionLoginFragmentToMainFragment()
            )
        }


        viewBinding.buttonGO.setOnClickListener {
            val fn = viewBinding.firstName.text.toString()
            val ln = viewBinding.lastName.text.toString()
            viewModel.saveUser(User(0, fn, ln)).let {
                if (it == "done") {
                    findNavController().navigateSafe(
                        LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    )
                }
            }
        }
    }



override val backPressedCallback: OnBackPressedCallback
    get() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
}
}