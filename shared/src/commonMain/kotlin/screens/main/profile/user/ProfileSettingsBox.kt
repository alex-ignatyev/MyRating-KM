package screens.main.profile.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screens.main.profile.user.UserAction.ClickOnSettings
import ui.MRTheme
import ui.components.MRDivider

@Composable
fun ProfileSettingsBox(doAction: (UserAction) -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MRTheme.colors.primary, RoundedCornerShape(8))
    ) {
        Column {
            SettingsItem("Настройки", modifier = Modifier.clickable {
                doAction.invoke(ClickOnSettings)
            })
            SettingsItem("Избранное")
            SettingsItem("Язык", false)
        }
    }
}

@Composable
fun SettingsItem(title: String, showDivider: Boolean = true, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 8.dp, bottom = 8.dp),
        text = title,
        style = MRTheme.typography.body
    )
    if (showDivider) MRDivider(modifier = Modifier.padding(start = 24.dp))
}
