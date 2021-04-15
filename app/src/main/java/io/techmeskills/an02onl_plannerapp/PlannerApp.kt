package io.techmeskills.an02onl_plannerapp

import android.app.Application
import io.techmeskills.an02onl_plannerapp.model.DatabaseConstructor
import io.techmeskills.an02onl_plannerapp.model.DB
import io.techmeskills.an02onl_plannerapp.screen.add.AddViewModel
import io.techmeskills.an02onl_plannerapp.screen.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PlannerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlannerApp)
            modules(listOf(viewModels, storageModule))
        }
    }

    private val viewModels = module {
        viewModel { MainViewModel(get()) }
        viewModel { AddViewModel(get()) }
    }


    private val storageModule = module {
        single { DatabaseConstructor.create(get()) }
        factory { get<DB>().notesDao() }
    }
}