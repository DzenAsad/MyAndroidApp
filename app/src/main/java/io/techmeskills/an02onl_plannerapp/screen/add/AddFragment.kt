package io.techmeskills.an02onl_plannerapp.screen.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.michaelflisar.dialogs.events.BaseDialogEvent
import com.michaelflisar.dialogs.events.DialogDateTimeEvent
import com.michaelflisar.dialogs.setups.DialogDateTime
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentAddBinding
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : NavigationFragment<FragmentAddBinding>(R.layout.fragment_add) {

    override val viewBinding: FragmentAddBinding by viewBinding()
    private val viewModel: AddViewModel by viewModel()

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    private val args: AddFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.buttonAdd.setOnClickListener {
            if (viewBinding.noteText.text.isNotBlank()) {
                args.note?.let {
                    viewModel.updateNote(
                        Note(
                            id = it.id,
                            title = viewBinding.noteText.text.toString(),
                            date = viewBinding.noteDate.text.toString(),
                            user = it.user,
                            alarmEnabled = viewBinding.switchAlarm.isChecked
                        )
                    )
                } ?: kotlin.run {
                    viewModel.addNewNote(
                        Note(
                            title = viewBinding.noteText.text.toString(),
                            date = viewBinding.noteDate.text.toString(),
                            user = "",
                            alarmEnabled = viewBinding.switchAlarm.isChecked
                        )
                    )
                }
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), " Please, enter your note", Toast.LENGTH_LONG)
                    .show()
            }
        }

        args.note?.let { note ->
            viewBinding.noteText.setText(note.title)
            viewBinding.noteDate.setText(note.date)
            viewBinding.switchAlarm.isChecked = note.alarmEnabled
            if (note.date.isNotEmpty()) {
                viewBinding.switchAlarm.isClickable = true
            }
        }

        viewBinding.noteDate.setOnClickListener {
            showDatePickerDialog(dateFormatter.parseWithoutException(viewBinding.noteDate.text.toString())?.time)
        }

        viewBinding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()

        }
    }

    private fun DateFormat.parseWithoutException(string: String): Date? {
        return try {
            parse(string)
        } catch (e: ParseException) {
            return null
        }
    }

    private fun showDatePickerDialog(date: Long?) {

        DialogDateTime(2)

            .create()
            .show(this)
    }

    override fun onDialogResultAvailable(event: BaseDialogEvent): Boolean {
        return when (event) {
            is DialogDateTimeEvent -> {
                event.posClicked().also {
                    if (it) viewBinding.noteDate.setText(
                        (dateFormatter.format(event.data!!.date.timeInMillis))
                    )
                    viewBinding.switchAlarm.isClickable = true
                }
            }
            else -> false
        }
    }

    override val backPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbar.setPadding(0, top, 0, 0)
        viewBinding.root.setPadding(0, 0, 0, bottom)
    }

}