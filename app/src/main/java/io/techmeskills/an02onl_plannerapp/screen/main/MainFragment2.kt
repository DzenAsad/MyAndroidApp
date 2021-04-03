package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMain2Binding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment2 : NavigationFragment<FragmentMain2Binding>(R.layout.fragment_main2) {

    override val viewBinding: FragmentMain2Binding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // finding the button
        val showButton = view.findViewById<Button>(R.id.button2)

        // finding the edit text
        val editText = view.findViewById<EditText>(R.id.nextNoteText)

        // Setting On Click Listener
        showButton.setOnClickListener {
            // Getting the user input
            val text = editText.text

            // Add user input like list
            viewModel.addNoteToList(text.toString())

            //Clear editText
            editText.setText("")
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }

}