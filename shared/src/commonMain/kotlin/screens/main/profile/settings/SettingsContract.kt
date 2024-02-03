package screens.main.profile.settings

sealed interface SettingsAction {
    data object OnLogOutClick : SettingsAction
    data object OnBackClick : SettingsAction
}

data class SettingsState(
    val isLoading: Boolean = true
)

sealed interface SettingsEffect
