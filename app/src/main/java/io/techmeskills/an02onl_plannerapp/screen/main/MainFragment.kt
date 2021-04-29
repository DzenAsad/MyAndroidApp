package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
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

        viewModel.progressLiveData.observe(this.viewLifecycleOwner) { success ->
            if (success.not()) {
                Toast.makeText(requireContext(), "Sync Failed", Toast.LENGTH_LONG)
                    .show()
            }
            viewBinding.syncImage.animation?.cancel()
        }

        val itemTouchHelper = ItemTouchHelper(adapter.simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(viewBinding.recyclerView)


    }

    private fun showCloudDialog() {
        val animation: () -> (Unit) = { viewBinding.syncImage.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.sync_anim))}
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cloud storage")
            .setMessage("Chose")
            .setPositiveButton("Import") { dialog, _ ->
                animation()
                viewModel.importNotes()
                dialog.cancel()
            }.setNegativeButton("Export") { dialog, _ ->
                animation()
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


