package screens.main.profile.change_password

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.AuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.main.profile.change_password.ChangePasswordAction.ChangeCurrentPassword
import screens.main.profile.change_password.ChangePasswordAction.ChangePassword
import screens.main.profile.change_password.ChangePasswordAction.ChangePasswordRepeat
import screens.main.profile.change_password.ChangePasswordAction.OnBackClick
import screens.main.profile.change_password.ChangePasswordAction.ResetPasswordClick
import screens.main.profile.change_password.ChangePasswordAction.ShowPasswordClick
import screens.main.profile.change_password.ChangePasswordAction.ShowPasswordCurrentClick
import screens.main.profile.change_password.ChangePasswordAction.ShowPasswordRepeatClick
import utils.BaseComponent
import utils.EMPTY
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultChangePasswordComponent(
    componentContext: ComponentContext,
    private val returnToPreviousScreen: () -> Unit
) : ChangePasswordComponent, BaseComponent<ChangePasswordEffect>(componentContext) {

    private val repository: AuthRepository by inject()
    override val state = MutableValue(ChangePasswordState())

    override fun doAction(action: ChangePasswordAction) {
        when (action) {
            is ChangeCurrentPassword -> changeCurrentPassword(action.value)
            is ShowPasswordCurrentClick -> currentPasswordVisibility()
            is ChangePassword -> changeNewPassword(action.value)
            is ShowPasswordClick -> changeNewPasswordVisibility()
            is ChangePasswordRepeat -> changeRepeatNewPassword(action.value)
            is ShowPasswordRepeatClick -> changeRepeatNewPasswordVisibility()
            is ResetPasswordClick -> resetPassword()
            is OnBackClick -> returnToPreviousScreen()
        }
    }

    private fun changeCurrentPassword(currentPassword: String) {
        state.value = state.value.copy(currentPassword = currentPassword, error = EMPTY)
    }

    private fun changeNewPassword(newPassword: String) {
        state.value = state.value.copy(newPassword = newPassword, error = EMPTY)
    }

    private fun changeRepeatNewPassword(repeatNewPassword: String) {
        state.value = state.value.copy(repeatNewPassword = repeatNewPassword, error = EMPTY)
    }

    private fun currentPasswordVisibility() {
        val passwordVisible = !state.value.isCurrentPasswordHidden
        state.value = state.value.copy(isCurrentPasswordHidden = passwordVisible)
    }

    private fun changeNewPasswordVisibility() {
        val passwordVisible = !state.value.isPasswordHidden
        state.value = state.value.copy(isPasswordHidden = passwordVisible)

    }

    private fun changeRepeatNewPasswordVisibility() {
        val passwordVisible = !state.value.isPasswordRepeatHidden
        state.value = state.value.copy(isPasswordRepeatHidden = passwordVisible)
    }

    private fun resetPassword() {
        if (checkFields()) return
        componentScope.launch {
            state.value = state.value.copy(isLoading = true)
            repository.changePassword(
                currentPassword = state.value.currentPassword,
                newPassword = state.value.newPassword,
                repeatNewPassword = state.value.repeatNewPassword
            ).onSuccess {
                returnToPreviousScreen
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun checkFields(): Boolean {
        if (state.value.currentPassword.length < 4 || state.value.newPassword.length < 4 || state.value.repeatNewPassword.length < 4) {
            state.value = state.value.copy(error = "Password should be more than 4 symbols")
            return true
        }
        return false
    }
}

interface ChangePasswordComponent {
    val state: Value<ChangePasswordState>
    fun doAction(action: ChangePasswordAction)
}
