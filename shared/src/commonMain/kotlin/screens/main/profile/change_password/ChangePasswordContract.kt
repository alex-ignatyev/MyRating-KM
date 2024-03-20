package screens.main.profile.change_password

sealed interface ChangePasswordAction {
    data class ChangeCurrentPassword(val value: String) : ChangePasswordAction
    data object ShowPasswordCurrentClick : ChangePasswordAction
    data class ChangePassword(val value: String) : ChangePasswordAction
    data object ShowPasswordClick : ChangePasswordAction
    data class ChangePasswordRepeat(val value: String) : ChangePasswordAction
    data object ShowPasswordRepeatClick : ChangePasswordAction
    data object ResetPasswordClick : ChangePasswordAction
    data object OnBackClick : ChangePasswordAction
}

data class ChangePasswordState(
    val isLoading: Boolean = false,
    val currentPassword: String = "",
    val isCurrentPasswordHidden: Boolean = true,
    val newPassword: String = "",
    val isPasswordHidden: Boolean = true,
    val repeatNewPassword: String = "",
    val isPasswordRepeatHidden: Boolean = true,
    val error: String = ""
)

sealed interface ChangePasswordEffect
