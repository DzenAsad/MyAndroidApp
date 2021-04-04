package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.screen.add.AddFragment
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModel()


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listLiveData.observe(this.viewLifecycleOwner, {
            viewBinding.recyclerView.adapter = NotesRecyclerViewAdapter(it)
        })


        viewBinding.button.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }

        setFragmentResultListener(AddFragment.ADD_NEW_RESULT) { key, bundle ->
            val note = bundle.getString(AddFragment.TEXT)
            val date = bundle.getString(AddFragment.DATE)
            note?.let {
                viewModel.addNoteToList(it, date)
            }
        }

    }
}


