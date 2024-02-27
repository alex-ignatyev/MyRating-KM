package screens.main.profile.user

sealed interface UserAction {
    data object InitProfileScreen : UserAction
    data object ClickOnSettings : UserAction
}

data class UserState(
    val isLoading: Boolean = true,
    val name: String = "",
    val login: String = ""
)

sealed interface UserEffect
