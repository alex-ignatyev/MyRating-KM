package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.my_rating.shared.AppRes
import screens.main.admin.admin_feed.AdminFeedScreen
import screens.main.profile.profile.ProfileScreen
import ui.components.Tab as InternalTab

internal data class FeedTab(
    override val index: Int = 0,
    override val title: String = AppRes.string.title_tobacco_feed,
    override val icon: ImageVector = Icons.Filled.Star
) : InternalTab

internal data class MixTab(
    override val index: Int = 1,
    override val title: String = "MixTab",
    override val icon: ImageVector = Icons.Filled.Search
) : InternalTab

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
