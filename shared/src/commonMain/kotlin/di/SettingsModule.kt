package di

import com.russhwolf.settings.Settings
import data.remote.SettingsDataSource
import org.koin.dsl.module

val settingsModule = module {
    single { Settings() }
    single { SettingsDataSource(get()) }
}
