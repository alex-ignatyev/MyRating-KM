package preview.screen.auth.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import screens.auth.account_create.AccountCreateState
import screens.auth.account_create.AccountCreateView
import ui.MRTheme

@Preview
@Composable
fun CreateAccountPreview_Light() {
    MRTheme(darkTheme = false) {
        AccountCreateView(state = AccountCreateState(), doAction = {})
    }
}

@Preview
@Composable
fun CreateAccountPreview_Night() {
    MRTheme(darkTheme = true) {
        AccountCreateView(state = AccountCreateState(), doAction = {})
    }
}
