package screens.auth.account_forgot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import screens.auth.account_forgot.AccountForgotAction.ChangeEmail
import screens.auth.account_forgot.AccountForgotAction.ChangePassword
import screens.auth.account_forgot.AccountForgotAction.ChangePasswordRepeat
import screens.auth.account_forgot.AccountForgotAction.OnBackClick
import screens.auth.account_forgot.AccountForgotAction.ResetPasswordClick
import screens.auth.account_forgot.AccountForgotAction.ShowPasswordClick
import screens.auth.account_forgot.AccountForgotAction.ShowPasswordRepeatClick
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRCircularProgress
import ui.components.MRTextError
import ui.components.MRTextField
import ui.components.MRToolbar
import ui.components.TextFieldType.Password
import ui.view.PasswordShowIcon

@Composable
fun AccountForgotView(
    state: AccountForgotState,
    rootModifier: Modifier = Modifier,
    doAction: (AccountForgotAction) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .background(MRTheme.colors.background)
            .windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = MRTheme.colors.background,
        topBar = {
            MRToolbar(
                onBackClick = {
                    doAction.invoke(OnBackClick)
                })
        }
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = AppResStrings.forgot_title,
                style = MRTheme.typography.header
            )
            Text(
                text = AppResStrings.forgot_subtitle,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                style = MRTheme.typography.body,
                color = MRTheme.colors.secondaryText
            )

            Spacer(modifier = Modifier.height(20.dp))

            MRTextField(
                value = state.email,
                placeholder = AppResStrings.forgot_email,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
            ) {
                doAction.invoke(ChangeEmail(it))
            }

            MRTextField(
                value = state.password,
                placeholder = AppResStrings.forgot_password,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
                fieldType = Password(state.isPasswordHidden),
                endIcon = {
                    PasswordShowIcon(state.isPasswordHidden) {
                        doAction.invoke(ShowPasswordClick)
                    }
                }
            ) {
                doAction.invoke(ChangePassword(it))
            }

            MRTextField(
                value = state.passwordRepeat,
                placeholder = AppResStrings.forgot_repeat_password,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
                fieldType = Password(state.isPasswordRepeatHidden),
                endIcon = {
                    PasswordShowIcon(state.isPasswordRepeatHidden) {
                        doAction.invoke(ShowPasswordRepeatClick)
                    }
                }
            ) {
                doAction.invoke(ChangePasswordRepeat(it))
            }

            MRButton(
                modifier = Modifier.padding(vertical = 32.dp),
                text = if (state.isLoading) null else AppRes.string.forgot_reset,
                enabled = !state.isLoading,
                content = {
                    MRCircularProgress()
                },
                onClick = {
                    doAction.invoke(ResetPasswordClick)
                })

            MRTextError(errorText = state.error)
        }
    }
}
