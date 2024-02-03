package screens.auth.account_login

sealed interface AccountLoginAction {
    data class ChangeLogin(val value: String) : AccountLoginAction
    data class ChangePassword(val value: String) : AccountLoginAction
    data object ShowPasswordClick : AccountLoginAction
    data object CreateAccountClick : AccountLoginAction
    data object ForgotPasswordClick : AccountLoginAction
    data object LoginClick : AccountLoginAction
}

data class AccountLoginState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val isPasswordHidden: Boolean = true,
    val error: String = ""
)

sealed interface AccountLoginEffect
