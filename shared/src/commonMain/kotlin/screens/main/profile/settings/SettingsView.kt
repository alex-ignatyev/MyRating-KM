package screens.main.profile.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.statusBars
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import data.LocalSettingsEventBus
import screens.main.profile.settings.SettingsAction.OnBackClick
import screens.main.profile.settings.SettingsAction.OnLogOutClick
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRDivider
import ui.components.MRToolbar

@Composable
fun SettingsView(state: SettingsState, doAction: (SettingsAction) -> Unit) {
    val settingsEventBus = LocalSettingsEventBus.current
    val currentSettings = settingsEventBus.currentSettings.value

    Scaffold(
        modifier = Modifier.background(MRTheme.colors.background)
            .windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = MRTheme.colors.background,
        topBar = {
            MRToolbar(title = AppResStrings.title_settings, onBackClick = {
                doAction.invoke(OnBackClick)
            })
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            Column {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = AppRes.string.action_dark_theme_enable,
                        color = MRTheme.colors.backgroundOn,
                        style = MRTheme.typography.body
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

                MRDivider()
            }

            MRButton(
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.BottomCenter),
                backgroundColor = MRTheme.colors.error,
                text = AppResStrings.text_logout
            ) {
                doAction.invoke(OnLogOutClick)
            }
        }
        //TODO Добавить смену пароля
    }
}
