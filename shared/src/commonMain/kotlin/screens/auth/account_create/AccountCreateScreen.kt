package screens.auth.account_create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun AccountCreateScreen(component: AccountCreateComponent, rootModifier: Modifier = Modifier) {

    val state by component.state.subscribeAsState()

    AccountCreateView(state, rootModifier) { action ->
        component.doAction(action)
    }
}
