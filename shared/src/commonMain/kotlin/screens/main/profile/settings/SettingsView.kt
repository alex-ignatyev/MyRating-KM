package screens.main.profile.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
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
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import com.moriatsushi.insetsx.navigationBars
import com.moriatsushi.insetsx.statusBars
import data.LocalSettingsEventBus
import screens.main.profile.settings.SettingsEvent.OnBackClick
import screens.main.profile.settings.SettingsEvent.OnLogOutClick
import ui.KalyanTheme
import ui.components.KalyanButton
import ui.components.KalyanDivider
import ui.components.KalyanToolbar

@Composable
fun SettingsView(state: SettingsState, obtainEvent: (SettingsEvent) -> Unit) {
    val settingsEventBus = LocalSettingsEventBus.current
    val currentSettings = settingsEventBus.currentSettings.value

    Scaffold(
        modifier = Modifier.background(KalyanTheme.colors.background)
            .windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = KalyanTheme.colors.background,
        topBar = {
            KalyanToolbar(title = AppResStrings.title_settings, onBackClick = {
                obtainEvent.invoke(OnBackClick())
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
                        color = KalyanTheme.colors.backgroundOn,
                        style = KalyanTheme.typography.body
                    )
                    Checkbox(
                        checked = currentSettings.isDarkMode, onCheckedChange = {
                            settingsEventBus.updateDarkMode(!currentSettings.isDarkMode)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = KalyanTheme.colors.backgroundOn,
                            uncheckedColor = KalyanTheme.colors.backgroundOn
                        )
                    )
                }

                KalyanDivider()
            }

            KalyanButton(
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.navigationBars.add(WindowInsets.navigationBars)),
                backgroundColor = KalyanTheme.colors.error,
                text = AppResStrings.text_logout
            ) {
                obtainEvent.invoke(OnLogOutClick())
            }
        }
        //TODO Добавить смену пароля
    }
}
