package screens.main.profile.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import data.LocalSettingsEventBus
import screens.main.profile.user.UserAction.ClickOnChangePassword
import screens.main.profile.user.UserAction.OnLogOutClick
import ui.MRTheme
import utils.clickableRipple
import utils.keyboardAsState

@Composable
fun ProfileSettingsBox(rootModifier: Modifier = Modifier, doAction: (UserAction) -> Unit) {

    val settingsEventBus = LocalSettingsEventBus.current
    val currentSettings = settingsEventBus.currentSettings.value
    val isKeyboardOpen by keyboardAsState()
    val buttonModifier = if (isKeyboardOpen) Modifier.windowInsetsPadding(WindowInsets.ime) else rootModifier

    Box(Modifier.fillMaxSize()) {
        Column {
            Row(
                modifier = Modifier.padding(top = 16.dp, bottom = 6.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppRes.string.action_dark_theme_enable,
                    textAlign = TextAlign.Start,
                    color = MRTheme.colors.backgroundOn,
                    style = MRTheme.typography.body,
                    modifier = Modifier
                        .weight(1f)
                )
                Checkbox(
                    checked = currentSettings.isDarkMode, onCheckedChange = {
                        settingsEventBus.updateDarkMode(!currentSettings.isDarkMode)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MRTheme.colors.backgroundOn,
                        uncheckedColor = MRTheme.colors.backgroundOn
                    )
                )
            }
            Row(
                modifier = Modifier
                    .height(height = 64.dp)
                    .padding(top = 16.dp, bottom = 6.dp, start = 16.dp, end = 12.dp)
                    .clickableRipple {
                        doAction(ClickOnChangePassword)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppRes.string.change_password,
                    textAlign = TextAlign.Start,
                    color = MRTheme.colors.backgroundOn,
                    style = MRTheme.typography.body,
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null
                )
            }
        }

        Text(
            text = AppResStrings.text_logout,
            color = MRTheme.colors.error,
            style = MRTheme.typography.body,
            modifier = buttonModifier
                .padding(16.dp)
                .align(Alignment.BottomCenter)
                .clickableRipple {
                    doAction.invoke(OnLogOutClick)
                }
        )
    }
}
