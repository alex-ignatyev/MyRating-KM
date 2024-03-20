package screens.auth.account_create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import screens.auth.account_create.AccountCreateAction.ChangeEmail
import screens.auth.account_create.AccountCreateAction.ChangeLogin
import screens.auth.account_create.AccountCreateAction.ChangePassword
import screens.auth.account_create.AccountCreateAction.ChangePasswordRepeat
import screens.auth.account_create.AccountCreateAction.ChangePhone
import screens.auth.account_create.AccountCreateAction.CreateAccountClick
import screens.auth.account_create.AccountCreateAction.OnBackClick
import screens.auth.account_create.AccountCreateAction.ShowPasswordClick
import screens.auth.account_create.AccountCreateAction.ShowPasswordRepeatClick
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRCircularProgress
import ui.components.MRTextError
import ui.components.MRTextField
import ui.components.MRToolbar
import ui.components.TextFieldType.Password
import ui.view.PasswordShowIcon
import utils.EMPTY
import utils.keyboardAsState

@Composable
fun AccountCreateView(state: AccountCreateState, doAction: (AccountCreateAction) -> Unit) {

    val isKeyboardOpen by keyboardAsState()

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.ime),
        backgroundColor = MRTheme.colors.background,
        topBar = {
            MRToolbar(
                title = if (isKeyboardOpen) AppResStrings.register_title else EMPTY,
                onBackClick = {
                    doAction.invoke(OnBackClick)
                })
        }
    ) {

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(!isKeyboardOpen) {
                Column(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = AppResStrings.register_title,
                        style = MRTheme.typography.header
                    )
                    Text(
                        text = AppResStrings.register_subtitle,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                        style = MRTheme.typography.body,
                        color = MRTheme.colors.secondaryText
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            MRTextField(
                value = state.login,
                placeholder = AppResStrings.register_login,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
            ) {
                doAction(ChangeLogin(it))
            }

            MRTextField(
                value = state.password,
                placeholder = AppResStrings.register_password,
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
                placeholder = AppResStrings.register_repeat_password,
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

            MRTextField(
                value = state.email,
                placeholder = AppResStrings.register_email,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
            ) {
                doAction(ChangeEmail(it))
            }

            MRTextField(
                value = state.phone,
                placeholder = AppResStrings.register_phone,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
            ) {
                doAction(ChangePhone(it))
            }

            MRTextError(errorText = state.error)

            MRButton(
                modifier = Modifier.padding(vertical = 32.dp),
                text = if (state.isLoading) null else AppRes.string.register_title,
                enabled = !state.isLoading,
                content = {
                    MRCircularProgress()
                },
                onClick = {
                    doAction(CreateAccountClick)
                })
        }
    }
}
