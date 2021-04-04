package io.techmeskills.an02onl_plannerapp.screen.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentAddBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment

class AddFragment : NavigationFragment<FragmentAddBinding>(R.layout.fragment_add) {

    override val viewBinding: FragmentAddBinding by viewBinding()
    private val viewModel: AddViewModel by viewBinding()

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
//        viewBinding.toolbar.setPadding(0, top, 0, 0)
//        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.buttonAdd.setOnClickListener {
            if (viewBinding.noteText.text.isNotBlank()) {
                setFragmentResult(ADD_NEW_RESULT, Bundle().apply {
                    putString(TEXT, viewBinding.noteText.text.toString())
                    val d = viewBinding.noteDate
                    putString(DATE, "${d.dayOfMonth}.${d.month}.${d.year}")
                })
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), " Please, enter your note", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }


    companion object {
        const val ADD_NEW_RESULT = "ADD_NEW_RESULT"
        const val TEXT = "TEXT"
        const val DATE = "DATE"
    }


}