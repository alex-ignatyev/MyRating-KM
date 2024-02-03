package screens.auth.account_login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moriatsushi.insetsx.ExperimentalSoftwareKeyboardApi
import com.moriatsushi.insetsx.ime
import com.moriatsushi.insetsx.navigationBars
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import screens.auth.account_login.AccountLoginAction.ChangeLogin
import screens.auth.account_login.AccountLoginAction.ChangePassword
import screens.auth.account_login.AccountLoginAction.CreateAccountClick
import screens.auth.account_login.AccountLoginAction.ForgotPasswordClick
import screens.auth.account_login.AccountLoginAction.LoginClick
import screens.auth.account_login.AccountLoginAction.ShowPasswordClick
import ui.KalyanTheme
import ui.components.KalyanButton
import ui.components.KalyanCircularProgress
import ui.components.KalyanTextField
import ui.components.TextFieldType.Password
import ui.view.PasswordShowIcon

@OptIn(ExperimentalSoftwareKeyboardApi::class)
@Composable
fun AccountLoginView(state: AccountLoginState = AccountLoginState(), doAction: (AccountLoginAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(KalyanTheme.colors.background)
            .windowInsetsPadding(WindowInsets.ime)
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = AppResStrings.title_login,
            modifier = Modifier.padding(top = 48.dp),
            style = KalyanTheme.typography.header
        )

        Text(
            text = AppResStrings.subtitle_login,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            style = KalyanTheme.typography.body,
            textAlign = TextAlign.Center,
            color = KalyanTheme.colors.secondaryText
        )

        Spacer(modifier = Modifier.height(40.dp))

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

        Row(modifier = Modifier.padding(end = 32.dp, top = 16.dp).fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = AppResStrings.text_password_forgot,
                color = KalyanTheme.colors.primary,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    doAction.invoke(ForgotPasswordClick)
                }
            )
        }

        KalyanButton(
            modifier = Modifier.padding(top = 24.dp).padding(vertical = 32.dp),
            text = if (state.isLoading) null else AppRes.string.title_login,
            enabled = !state.isLoading,
            content = {
                KalyanCircularProgress()
            },
            onClick = { doAction(LoginClick) }
        )

        Text(
            text = state.error,
            color = KalyanTheme.colors.error,
            modifier = Modifier.padding(top = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = AppResStrings.text_account_dont_have,
                color = KalyanTheme.colors.outline
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = AppResStrings.text_account_create_one,
                color = KalyanTheme.colors.primary,
                style = KalyanTheme.typography.body,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    doAction.invoke(CreateAccountClick)
                }
            )
        }
    }
}
