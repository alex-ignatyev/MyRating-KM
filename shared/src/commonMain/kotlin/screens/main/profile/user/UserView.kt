package screens.main.profile.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Man
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.moriatsushi.insetsx.statusBars
import ui.MRTheme
import ui.components.MRDivider

@Composable
fun ProfileView(
    state: UserState,
    rootModifier: Modifier = Modifier,
    doAction: (UserAction) -> Unit
) {

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        backgroundColor = MRTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Default.Man,
                colorFilter = ColorFilter.tint(MRTheme.colors.primaryText),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(2.dp, MRTheme.colors.primaryText, CircleShape)
            )
            Text(
                text = state.login,
                modifier = Modifier
                    .fillMaxWidth(),
                style = MRTheme.typography.header
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.phone,
                    modifier = Modifier.padding(end = 4.dp),
                    style = MRTheme.typography.hint
                )
                Box(
                    modifier = Modifier
                        .background(MRTheme.colors.primary, CircleShape)
                        .size(4.dp)
                ) {}
                Text(
                    text = state.email,
                    modifier = Modifier.padding(start = 4.dp),
                    style = MRTheme.typography.hint
                )
            }
            MRDivider(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
            ProfileSettingsBox(rootModifier, doAction)
        }
    }
}
