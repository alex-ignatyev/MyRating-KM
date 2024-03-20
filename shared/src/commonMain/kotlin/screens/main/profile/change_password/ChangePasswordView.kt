package screens.main.profile.change_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import screens.main.profile.change_password.ChangePasswordAction.ChangeCurrentPassword
import screens.main.profile.change_password.ChangePasswordAction.ChangePassword
import screens.main.profile.change_password.ChangePasswordAction.ChangePasswordRepeat
import screens.main.profile.change_password.ChangePasswordAction.OnBackClick
import screens.main.profile.change_password.ChangePasswordAction.ResetPasswordClick
import screens.main.profile.change_password.ChangePasswordAction.ShowPasswordClick
import screens.main.profile.change_password.ChangePasswordAction.ShowPasswordCurrentClick
import screens.main.profile.change_password.ChangePasswordAction.ShowPasswordRepeatClick
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRCircularProgress
import ui.components.MRTextError
import ui.components.MRTextField
import ui.components.MRToolbar
import ui.components.TextFieldType.Password
import ui.components.ToolbarBackIcon.Close
import ui.view.PasswordShowIcon
import utils.keyboardAsState

@Composable
fun ChangePasswordView(
    state: ChangePasswordState,
    rootModifier: Modifier = Modifier,
    doAction: (ChangePasswordAction) -> Unit
) {

    val isKeyboardOpen by keyboardAsState()
    val buttonModifier = if (isKeyboardOpen) Modifier.windowInsetsPadding(WindowInsets.ime) else rootModifier

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            MRToolbar(
                backIcon = Close,
                onBackClick = {
                    doAction(OnBackClick)
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MRTheme.colors.background)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MRTextField(
                    value = state.currentPassword,
                    placeholder = AppResStrings.change_current_password,
                    enabled = !state.isLoading,
                    isError = state.error.isNotBlank(),
                    fieldType = Password(state.isCurrentPasswordHidden),
                    endIcon = {
                        PasswordShowIcon(state.isCurrentPasswordHidden) {
                            doAction(ShowPasswordCurrentClick)
                        }
                    }
                ) {
                    doAction(ChangeCurrentPassword(it))
                }

                MRTextField(
                    value = state.newPassword,
                    placeholder = AppResStrings.change_new_password,
                    enabled = !state.isLoading,
                    isError = state.error.isNotBlank(),
                    fieldType = Password(state.isPasswordHidden),
                    endIcon = {
                        PasswordShowIcon(state.isPasswordHidden) {
                            doAction(ShowPasswordClick)
                        }
                    }
                ) {
                    doAction(ChangePassword(it))
                }

                MRTextField(
                    value = state.repeatNewPassword,
                    placeholder = AppResStrings.change_repeat_password,
                    enabled = !state.isLoading,
                    isError = state.error.isNotBlank(),
                    fieldType = Password(state.isPasswordRepeatHidden),
                    endIcon = {
                        PasswordShowIcon(state.isPasswordRepeatHidden) {
                            doAction(ShowPasswordRepeatClick)
                        }
                    }
                ) {
                    doAction(ChangePasswordRepeat(it))
                }

                MRTextError(errorText = state.error)
            }

            MRButton(
                modifier = buttonModifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.BottomCenter),
                text = if (state.isLoading) null else AppRes.string.change_password_button,
                enabled = !state.isLoading,
                content = {
                    MRCircularProgress()
                },
                onClick = {
                    doAction(ResetPasswordClick)
                }
            )
        }
    }
}
