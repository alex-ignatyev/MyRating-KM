package screens.main.profile.user

sealed interface UserAction {
    data object InitProfileScreen : UserAction
    data object ClickOnChangePassword : UserAction
    data object OnLogOutClick : UserAction
}

data class UserState(
    val isLoading: Boolean = true,
    val login: String = "",
    val phone: String = "",
    val email: String = ""
)

sealed interface UserEffect
