package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.my_rating.shared.AppRes
import ui.components.Tab as InternalTab

internal data class FeedTab(
    override val index: Int = 0,
    override val title: String = AppRes.string.title_tobacco_feed,
    override val icon: ImageVector = Icons.Filled.Star
) : InternalTab

internal data class MixTab(
    override val index: Int = 1,
    override val title: String = AppRes.string.title_profile,
    override val icon: ImageVector = Icons.Filled.Person
) : InternalTab
