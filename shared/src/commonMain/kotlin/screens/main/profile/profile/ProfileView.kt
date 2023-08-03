package screens.main.profile.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my_rating.shared.strings.AppResStrings
import com.moriatsushi.insetsx.statusBars
import screens.main.profile.profile.ProfileEvent.ClickOnSettings
import ui.KalyanTheme
import ui.components.KalyanToolbar

@Composable
fun ProfileView(state: ProfileState, obtainEvent: (ProfileEvent) -> Unit) {
    Scaffold (
        modifier = Modifier.background(KalyanTheme.colors.background)
            .windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = KalyanTheme.colors.background,
        topBar = {
            KalyanToolbar(title = AppResStrings.title_profile, onFirstIconClick = {
                obtainEvent.invoke(ClickOnSettings())
            })
        }
    ) {

        Row(
            modifier = Modifier.padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.Person,
                colorFilter = ColorFilter.tint(KalyanTheme.colors.backgroundOn),
                contentDescription = null,
                modifier = Modifier
                    .size(84.dp)
                    .clickable {

                    }
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column {
                Text(text = state.name, color = KalyanTheme.colors.backgroundOn, fontSize = 20.sp)
            }

            //TODO Добавить избранные табаки списком вправо с переходом на грид
            //TODO Добавить табаки которые хочет покурить списком вправо с переходом на грид
            //TODO Добавить достижения
        }
    }
}
