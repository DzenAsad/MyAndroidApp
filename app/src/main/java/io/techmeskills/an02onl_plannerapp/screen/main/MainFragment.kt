package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.michaelflisar.dialogs.events.BaseDialogEvent
import com.michaelflisar.dialogs.events.DialogInputEvent
import com.michaelflisar.dialogs.setups.DialogInput
import com.michaelflisar.text.asText
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


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
            it.let {
                val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
                    ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP,
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    ) {

                    override fun getMovementFlags(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                    ): Int {
                        if (viewHolder.itemViewType == NotesRecyclerViewAdapter.ADD) return 0 //Protect add button from delete
                        return super.getMovementFlags(recyclerView, viewHolder)
                    }

                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder,
                    ): Boolean {
                        val fromPos = viewHolder.adapterPosition
                        val toPos = target.adapterPosition
                        recyclerView.adapter!!.notifyItemMoved(fromPos, toPos)
                        Collections.swap(it, fromPos, toPos)
                        return true
                    }

                    override fun isLongPressDragEnabled(): Boolean {
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                        val position = viewHolder.adapterPosition
                        viewModel.deleteNote(adapter.currentList[position])
                    }

                }
                ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(viewBinding.recyclerView)
            }
            adapter.submitList(it)
        }

        viewModel.currentUser.observe(this.viewLifecycleOwner) {
            if (it.name.isEmpty()) {
                findNavController().popBackStack()
            } else {
                viewBinding.titleText.text = it.name
            }

        }

        viewBinding.titleText.setOnClickListener {
            showUserEditDialog()

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

        viewModel.progressEditUser.observe(this.viewLifecycleOwner) { success ->
            if (success.not()) {
                Toast.makeText(requireContext(), "User with tis name already exist!", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    override fun onPause() {
        super.onPause()
        viewModel.notesLiveData.value?.let { viewModel.updatePos(it.drop(1)) }
    }

    override fun onDialogResultAvailable(event: BaseDialogEvent): Boolean {
        return when (event) {
            is DialogInputEvent -> {
                event.negClicked().also {
                    if (it) viewModel.delCurrUser()
                }
                event.posClicked().also {
                    if (it) viewModel.updtCurrUserAsync(event.data!!.input)
                }
            }
            else -> false
        }
    }

    private fun showUserEditDialog() {
        val di =
            DialogInput.InputField(initialText = viewBinding.titleText.text.toString().asText())
        DialogInput(input = di, id = 1, title = "Edit User".asText(), negButton = "Delete".asText())
            .create()
            .show(this)

    }

    private fun showCloudDialog() {
        val animation: () -> (Unit) = {
            viewBinding.syncImage.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.sync_anim
                )
            )
        }
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


