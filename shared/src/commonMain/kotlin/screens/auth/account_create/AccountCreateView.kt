package screens.auth.account_create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import screens.auth.account_create.AccountCreateAction.ChangeLogin
import screens.auth.account_create.AccountCreateAction.ChangeName
import screens.auth.account_create.AccountCreateAction.ChangePassword
import screens.auth.account_create.AccountCreateAction.ChangePasswordRepeat
import screens.auth.account_create.AccountCreateAction.CreateAccountClick
import screens.auth.account_create.AccountCreateAction.OnBackClick
import screens.auth.account_create.AccountCreateAction.ShowPasswordClick
import screens.auth.account_create.AccountCreateAction.ShowPasswordRepeatClick
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRCircularProgress
import ui.components.MRTextField
import ui.components.MRToolbar
import ui.components.TextFieldType.Password
import ui.view.PasswordShowIcon

@Composable
fun AccountCreateView(state: AccountCreateState, doAction: (AccountCreateAction) -> Unit) {

    Scaffold(
        modifier = Modifier.background(MRTheme.colors.background)
            .windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = MRTheme.colors.background,
        topBar = {
            MRToolbar(isTransparent = true, onBackClick = {
                doAction.invoke(OnBackClick)
            })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = AppResStrings.text_account_create,
                style = MRTheme.typography.header
            )

            Text(
                text = AppResStrings.subtitle_forgot,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                style = MRTheme.typography.body,
                color = MRTheme.colors.secondaryText
            )

            Spacer(modifier = Modifier.height(20.dp))

            MRTextField(
                value = state.login,
                placeholder = AppResStrings.text_login,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
            ) {
                doAction(ChangeLogin(it))
            }

            MRTextField(
                value = state.name,
                placeholder = AppResStrings.text_account_name,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank()
            ) {
                doAction(ChangeName(it))
            }

            MRTextField(
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

            MRTextField(
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

            MRButton(
                modifier = Modifier.padding(vertical = 32.dp),
                text = if (state.isLoading) null else AppRes.string.text_account_create,
                enabled = !state.isLoading,
                content = {
                    MRCircularProgress()
                },
                onClick = {
                    doAction(CreateAccountClick)
                })

            Text(
                text = state.error,
                color = MRTheme.colors.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
