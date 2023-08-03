package screens.main.profile.profile

sealed class ProfileEvent {
    class InitProfileScreen : ProfileEvent()
    class ClickOnSettings : ProfileEvent()
    class ClearActions : ProfileEvent()
}

data class ProfileState(
    val isLoading: Boolean = true,
    val name: String = "Test Name"
)

sealed class ProfileAction {
    class OpenSettingsScreen() : ProfileAction()
}
