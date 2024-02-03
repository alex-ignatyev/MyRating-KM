package screens.auth.account_create

sealed interface AccountCreateAction {
    data class ChangeLogin(val value: String) : AccountCreateAction
    data class ChangeName(val value: String) : AccountCreateAction
    data class ChangePassword(val value: String) : AccountCreateAction
    data object ShowPasswordClick : AccountCreateAction
    data class ChangePasswordRepeat(val value: String) : AccountCreateAction
    data object ShowPasswordRepeatClick : AccountCreateAction
    data object CreateAccountClick : AccountCreateAction
    data object OnBackClick : AccountCreateAction
}

data class AccountCreateState(
    val isLoading: Boolean = false,
    val login: String = "",
    val name: String = "",
    val password: String = "",
    val isPasswordHidden: Boolean = true,
    val passwordRepeat: String = "",
    val isPasswordRepeatHidden: Boolean = true,
    val error: String = ""
)

sealed interface AccountCreateEffect
