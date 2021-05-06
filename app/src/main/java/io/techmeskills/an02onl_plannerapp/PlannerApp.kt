package io.techmeskills.an02onl_plannerapp

import android.app.Application
import io.techmeskills.an02onl_plannerapp.model.DB
import io.techmeskills.an02onl_plannerapp.model.DatabaseConstructor
import io.techmeskills.an02onl_plannerapp.model.alarm.MyAlarmManager
import io.techmeskills.an02onl_plannerapp.model.cloud.ApiInterface
import io.techmeskills.an02onl_plannerapp.model.modules.AlarmModule
import io.techmeskills.an02onl_plannerapp.model.modules.CloudModule
import io.techmeskills.an02onl_plannerapp.model.modules.NoteModule
import io.techmeskills.an02onl_plannerapp.model.modules.UserModule
import io.techmeskills.an02onl_plannerapp.model.preferences.SettingsStore
import io.techmeskills.an02onl_plannerapp.screen.add.AddViewModel
import io.techmeskills.an02onl_plannerapp.screen.login.LoginViewModel
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
            modules(listOf(viewModels, storageModule, settingsStore, cloudModule, alarmModule))
        }
    }

    private val viewModels = module {
        viewModel { LoginViewModel(get()) }
        viewModel { MainViewModel(get(), get(), get()) }
        viewModel { AddViewModel(get(), get()) }
    }


    private val storageModule = module {
        single { DatabaseConstructor.create(get()) }
        factory { get<DB>().notesDao() }
        factory { get<DB>().usersDao() }
        single { SettingsStore(get()) }
    }

    private val settingsStore = module {  //создаем репозитории
        factory { UserModule(get(), get(), get()) }
        factory { NoteModule(get(), get(), get()) }
        factory { CloudModule(get(), get(), get()) }
        factory { AlarmModule(get(), get()) }
    }

    private val cloudModule = module {
        factory { ApiInterface.get() }
    }

    private val alarmModule = module {
        single { MyAlarmManager() }
    }

}