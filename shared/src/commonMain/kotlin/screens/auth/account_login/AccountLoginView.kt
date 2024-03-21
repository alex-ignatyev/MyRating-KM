package screens.auth.account_login

import androidx.compose.foundation.background
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
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRCircularProgress
import ui.components.MRTextError
import ui.components.MRTextField
import ui.components.TextFieldType.Password
import ui.view.PasswordShowIcon
import utils.clickableRipple

@OptIn(ExperimentalSoftwareKeyboardApi::class)
@Composable
fun AccountLoginView(
    state: AccountLoginState = AccountLoginState(),
    rootModifier: Modifier = Modifier,
    doAction: (AccountLoginAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MRTheme.colors.background)
            .windowInsetsPadding(WindowInsets.ime)
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = AppResStrings.title_login,
            modifier = Modifier.padding(top = 48.dp),
            style = MRTheme.typography.header
        )
        Text(
            text = AppResStrings.subtitle_login,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            style = MRTheme.typography.body,
            color = MRTheme.colors.secondaryText
        )

        Spacer(modifier = Modifier.height(40.dp))

        MRTextField(
            value = state.login,
            placeholder = AppResStrings.text_login,
            enabled = !state.isLoading,
            isError = state.error.isNotBlank(),
        ) {
            doAction.invoke(ChangeLogin(it))
        }

        MRTextField(
            value = state.password,
            placeholder = AppResStrings.text_password,
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

        Row(
            modifier = Modifier
                .padding(end = 32.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = AppResStrings.text_password_forgot,
                color = MRTheme.colors.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickableRipple {
                        doAction.invoke(ForgotPasswordClick)
                    }
            )
        }

        MRButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(vertical = 32.dp),
            text = if (state.isLoading) null else AppRes.string.title_login,
            enabled = !state.isLoading,
            content = {
                MRCircularProgress()
            }) {
            doAction.invoke(LoginClick)
        }

        MRTextError(errorText = state.error)

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
                color = MRTheme.colors.outline
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = AppResStrings.text_account_create_one,
                color = MRTheme.colors.primary,
                style = MRTheme.typography.body,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickableRipple {
                        doAction.invoke(CreateAccountClick)
                    }
            )
        }
    }
}
