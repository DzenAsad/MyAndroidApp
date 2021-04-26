package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModel()

    private val adapter = NotesRecyclerViewAdapter(
        onClick = { note ->
            findNavController().navigateSafe(
                MainFragmentDirections.actionMainFragmentToAddFragment(
                    note
                )
            )
        },
        onDelete = {
            viewModel.deleteNote(it)
        },
        onAdd = {
            findNavController().navigateSafe(
                MainFragmentDirections.actionMainFragmentToAddFragment(
                    null
                )
            )
        }
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewBinding.recyclerView.adapter = adapter

        viewModel.notesLiveData.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.currentUser.observe(this.viewLifecycleOwner) {
            if (it.userId == -1L) {
                findNavController().popBackStack()
            } else {
                viewBinding.toolbar.title = it.firstName
                viewBinding.toolbar.subtitle = it.lastName
            }

        }

        viewBinding.syncImage.setOnClickListener {
            showCloudDialog()
        }

        viewBinding.toolbar.setNavigationOnClickListener {
            viewModel.logout()

        }

        val itemTouchHelper = ItemTouchHelper(adapter.simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(viewBinding.recyclerView)


    }

    private fun showCloudDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cloud storage")
            .setMessage("Chose")
            .setPositiveButton("Import") { dialog, _ ->
//                viewBinding.progressIndicator.isVisible = true
                viewModel.importNotes()
                dialog.cancel()
            }.setNegativeButton("Export") { dialog, _ ->
//                viewBinding.progressIndicator.isVisible = true
                viewModel.exportNotes()
                dialog.cancel()
            }.show()
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
}


