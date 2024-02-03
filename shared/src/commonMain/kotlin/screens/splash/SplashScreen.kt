package screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.my_rating.shared.strings.AppResStrings
import screens.splash.SplashAction.InitScreen
import ui.KalyanTheme

@Composable
fun SplashScreen(component: SplashComponent) {

    Column(
        modifier = Modifier.fillMaxSize().background(KalyanTheme.colors.background),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = AppResStrings.app_name,
            style = KalyanTheme.typography.header,
            color = KalyanTheme.colors.backgroundOn,
            textAlign = TextAlign.Center
        )
    }

    LaunchedEffect(Unit) {
        component.doAction(InitScreen)
    }
}
