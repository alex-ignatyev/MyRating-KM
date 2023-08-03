package navigation

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.my_rating.shared.AppRes
import screens.main.admin.admin_feed.AdminFeedScreen
import screens.main.mix.mix_feed.MixFeedScreen
import screens.main.profile.profile.ProfileScreen
import screens.main.tobacco.tobacco_feed.TobaccoFeedScreen

internal object TobaccoFeedTab : Tab {
    @Composable
    override fun Content() {
        Navigator(TobaccoFeedScreen)
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Filled.Star) //TODO Cменить иконку

            return remember {
                TabOptions(
                    index = 0u,
                    title = AppRes.string.title_tobacco_feed,
                    icon = icon
                )
            }
        }
}

internal object MixTab : Tab {
    @Composable
    override fun Content() {
        Navigator(MixFeedScreen)
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Filled.Place) //TODO Cменить иконку

            return remember {
                TabOptions(
                    index = 1u,
                    title = AppRes.string.title_mix,
                    icon = icon
                )
            }
        }
}

internal object AdminTab : Tab {
    @Composable
    override fun Content() {
        Navigator(AdminFeedScreen)
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Filled.Search) //TODO Cменить иконку

            return remember {
                TabOptions(
                    index = 2u,
                    title = AppRes.string.title_admin,
                    icon = icon
                )
            }
        }
}

internal object ProfileTab : Tab {
    @Composable
    override fun Content() {
        Navigator(ProfileScreen)
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Filled.Person) //TODO Cменить иконку ?

            return remember {
                TabOptions(
                    index = 3u,
                    title = AppRes.string.title_profile,
                    icon = icon
                )
            }
        }
}
