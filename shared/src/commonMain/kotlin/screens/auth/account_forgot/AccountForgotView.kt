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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import screens.auth.account_forgot.AccountForgotAction.ChangeLogin
import screens.auth.account_forgot.AccountForgotAction.ChangePassword
import screens.auth.account_forgot.AccountForgotAction.ChangePasswordRepeat
import screens.auth.account_forgot.AccountForgotAction.OnBackClick
import screens.auth.account_forgot.AccountForgotAction.ResetPasswordClick
import screens.auth.account_forgot.AccountForgotAction.ShowPasswordClick
import screens.auth.account_forgot.AccountForgotAction.ShowPasswordRepeatClick
import ui.KalyanTheme
import ui.components.KalyanButton
import ui.components.KalyanCircularProgress
import ui.components.KalyanTextField
import ui.components.KalyanToolbar
import ui.components.TextFieldType.Password
import ui.view.PasswordShowIcon

@Composable
fun AccountForgotView(state: AccountForgotState, doAction: (AccountForgotAction) -> Unit) {

    Scaffold(
        modifier = Modifier.background(KalyanTheme.colors.background)
            .windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = KalyanTheme.colors.background,
        topBar = {
            KalyanToolbar(isTransparent = true, onBackClick = {
                doAction.invoke(OnBackClick)
            })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = AppResStrings.title_forgot,
                style = KalyanTheme.typography.header
            )

            Text(
                text = AppResStrings.subtitle_forgot,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                style = KalyanTheme.typography.body,
                textAlign = TextAlign.Center,
                color = KalyanTheme.colors.secondaryText
            )

            Spacer(modifier = Modifier.height(20.dp))

            KalyanTextField(
                value = state.login,
                placeholder = AppResStrings.text_login,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
            ) {
                doAction(ChangeLogin(it))
            }

            KalyanTextField(
                value = state.password,
                placeholder = AppResStrings.text_password,
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

            KalyanTextField(
                value = state.passwordRepeat,
                placeholder = AppResStrings.text_password_repeat,
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

            KalyanButton(
                modifier = Modifier.padding(vertical = 32.dp),
                text = if (state.isLoading) null else AppRes.string.text_forgot_reset,
                enabled = !state.isLoading,
                content = {
                    KalyanCircularProgress()
                },
                onClick = {
                    doAction(ResetPasswordClick)
                })

            Text(
                text = state.error,
                color = KalyanTheme.colors.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
