package screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.my_rating.shared.strings.AppResStrings
import screens.splash.SplashAction.InitScreen
import ui.MRTheme

@Composable
fun SplashScreen(component: SplashComponent, rootModifier: Modifier = Modifier) {

    Column(
        modifier = rootModifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = AppResStrings.app_name,
            style = MRTheme.typography.header,
            color = MRTheme.colors.backgroundOn
        )
    }

    LaunchedEffect(Unit) {
        component.doAction(InitScreen)
    }
}
