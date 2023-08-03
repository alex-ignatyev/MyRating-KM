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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import com.moriatsushi.insetsx.statusBars
import screens.auth.account_create.AccountCreateEvent.ChangeLogin
import screens.auth.account_create.AccountCreateEvent.ChangeName
import screens.auth.account_create.AccountCreateEvent.ChangePassword
import screens.auth.account_create.AccountCreateEvent.ChangePasswordRepeat
import screens.auth.account_create.AccountCreateEvent.CreateAccountClick
import screens.auth.account_create.AccountCreateEvent.OnBackClick
import screens.auth.account_create.AccountCreateEvent.ShowPasswordClick
import screens.auth.account_create.AccountCreateEvent.ShowPasswordRepeatClick
import ui.view.PasswordShowIcon
import ui.KalyanTheme
import ui.components.KalyanButton
import ui.components.KalyanCircularProgress
import ui.components.KalyanTextField
import ui.components.KalyanToolbar
import ui.components.TextFieldType.Password
import utils.mvi.Event

@Composable
fun AccountCreateView(state: AccountCreateState, obtainEvent: (Event) -> Unit) {

    Scaffold(
        modifier = Modifier.background(KalyanTheme.colors.background)
            .windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = KalyanTheme.colors.background,
        topBar = {
            KalyanToolbar(isTransparent = true, onBackClick = {
                obtainEvent.invoke(OnBackClick())
            })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = AppResStrings.text_account_create,
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
                obtainEvent(ChangeLogin(it))
            }

            KalyanTextField(
                value = state.name,
                placeholder = AppResStrings.text_account_name,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank()
            ) {
                obtainEvent(ChangeName(it))
            }

            KalyanTextField(
                value = state.password,
                placeholder = AppResStrings.text_password,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
                fieldType = Password(state.isPasswordHidden),
                endIcon = {
                    PasswordShowIcon(state.isPasswordHidden) {
                        obtainEvent(ShowPasswordClick())
                    }
                }
            ) {
                obtainEvent(ChangePassword(it))
            }

            KalyanTextField(
                value = state.passwordRepeat,
                placeholder = AppResStrings.text_password_repeat,
                enabled = !state.isLoading,
                isError = state.error.isNotBlank(),
                fieldType = Password(state.isPasswordRepeatHidden),
                endIcon = {
                    PasswordShowIcon(state.isPasswordRepeatHidden) {
                        obtainEvent(ShowPasswordRepeatClick())
                    }
                }
            ) {
                obtainEvent(ChangePasswordRepeat(it))
            }

            KalyanButton(
                modifier = Modifier.padding(vertical = 32.dp),
                text = if (state.isLoading) null else AppRes.string.text_account_create,
                enabled = !state.isLoading,
                content = {
                    KalyanCircularProgress()
                },
                onClick = {
                    obtainEvent(CreateAccountClick())
                })

            Text(
                text = state.error,
                color = KalyanTheme.colors.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
