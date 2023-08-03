package di

import com.russhwolf.settings.Settings
import data.SettingsDataSource
import org.koin.dsl.module

val settingsModule = module {
    single { Settings() }
    single { SettingsDataSource(get()) }
}
