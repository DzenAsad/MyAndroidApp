package io.techmeskills.an02onl_plannerapp.screen.add

import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentAddBinding
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.screen.main.MainViewModel
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment : NavigationFragment<FragmentAddBinding>(R.layout.fragment_add) {

    override val viewBinding: FragmentAddBinding by viewBinding()
//    private val viewModel: MainViewModel by viewModel()

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
//        viewBinding.toolbar.setPadding(0, top, 0, 0)
//        viewBinding.recyclerView.setPadding(0, 0, 0, bottom)
    }
}