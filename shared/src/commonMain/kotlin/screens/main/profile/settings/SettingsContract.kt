package screens.main.profile.settings

sealed class SettingsEvent {
    class OnLogOutClick : SettingsEvent()
    class OnBackClick : SettingsEvent()
}

data class SettingsState(
    val isLoading: Boolean = true
)

sealed class SettingsAction {
    class OpenLoginScreen : SettingsAction()
    class ReturnBack : SettingsAction()
}
