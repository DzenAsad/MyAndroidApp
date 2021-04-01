package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listLiveData.observe(this.viewLifecycleOwner, {
            viewBinding.recyclerView.adapter = NotesRecyclerViewAdapter(it)
        })

        // finding the button
        val showButton = view.findViewById<Button>(R.id.button)

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


}