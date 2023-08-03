package data

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsEventBus : KoinComponent {

    val settings: SettingsDataSource by inject()

    private val _currentSettings: MutableStateFlow<SettingsBundle> = MutableStateFlow(
        SettingsBundle(
            isDarkMode = settings.isDarkMode
        )
    )
    val currentSettings: StateFlow<SettingsBundle> = _currentSettings

    fun updateDarkMode(isDarkMode: Boolean) {
        settings.isDarkMode = isDarkMode
        _currentSettings.value = _currentSettings.value.copy(isDarkMode = isDarkMode)
    }

    fun isDarkMode(): Boolean {
        return _currentSettings.value.isDarkMode
    }
}

data class SettingsBundle(
    val isDarkMode: Boolean
)

internal val LocalSettingsEventBus = staticCompositionLocalOf { SettingsEventBus() }
