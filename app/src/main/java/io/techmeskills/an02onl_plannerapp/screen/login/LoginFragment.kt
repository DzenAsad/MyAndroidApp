package io.techmeskills.an02onl_plannerapp.screen.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
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

        val editTextList = listOf(viewBinding.firstName, viewBinding.lastName)
        editTextList.forEach { editText ->
            editText.onRightDrawableClicked {
                editText.setText("")
            }
            editText.doAfterTextChanged {
                it?.let {
                    if (it.isNotEmpty()) {
                        editText.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_baseline_cancel_24,
                            0
                        )
                    } else {
                        editText.setCompoundDrawables(null, null, null, null)
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
        this.setOnTouchListener { v, event ->
            var hasConsumed = false
            if (v is EditText) {
                if (event.x >= v.width - v.totalPaddingRight) {
                    if (event.action == MotionEvent.ACTION_UP) {
                        onClicked(this)
                    }
                    hasConsumed = true
                }
            }
            hasConsumed
        }
    }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
    }


}