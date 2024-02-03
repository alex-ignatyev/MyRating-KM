package screens.auth.account_forgot

sealed interface AccountForgotAction {
    data class ChangeLogin(val value: String) : AccountForgotAction
    data class ChangePassword(val value: String) : AccountForgotAction
    data object ShowPasswordClick : AccountForgotAction
    data class ChangePasswordRepeat(val value: String) : AccountForgotAction
    data object ShowPasswordRepeatClick : AccountForgotAction
    data object ResetPasswordClick : AccountForgotAction
    data object OnBackClick : AccountForgotAction
}

data class AccountForgotState(
    val isLoading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val isPasswordHidden: Boolean = true,
    val passwordRepeat: String = "",
    val isPasswordRepeatHidden: Boolean = true,
    val error: String = ""
)

sealed interface AccountForgotEffect
