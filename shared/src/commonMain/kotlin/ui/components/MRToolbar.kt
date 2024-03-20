package ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ui.MRTheme
import utils.AnimationVisibility
import utils.clickableRipple

/**
 * [title] Отображает заголовок
 *
 * [backIcon] Позволяют задать иконку возврата (ArrowBack, Close), отображается только когда задано значение для действия [onBackClick]
 *
 * [onBackClick] Действие выполнемое при нажатии на [backIcon]
 *
 * [actionRightIcon] Позволяют задать иконку доп. действия (Add), отображается только когда задано значение для действия [onRightIconActionClick]
 *
 * [onRightIconActionClick] Действие выполнемое при нажатии на [actionRightIcon]
 */

@Composable
fun MRToolbar(
    title: String = "",
    titleAnimate: Boolean = false,
    modifier: Modifier = Modifier,
    backIcon: ToolbarBackIcon = ToolbarBackIcon.Back,
    actionLeftIcon: ToolbarActionIcon = ToolbarActionIcon.Edit,
    actionRightIcon: ToolbarActionIcon = ToolbarActionIcon.Add,
    onBackClick: (() -> Unit)? = null,
    onLeftIconActionClick: (() -> Unit)? = null,
    onRightIconActionClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MRTheme.colors.background)
            .padding(top = 16.dp)
    ) {
        // Кнопка назад или крестик
        AnimationVisibility(
            visibility = onBackClick != null,
            offsetX = -300
        ) {
            Image(
                imageVector = backIcon.icon,
                colorFilter = ColorFilter.tint(MRTheme.colors.backgroundOn),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .clickableRipple {
                        onBackClick?.invoke()
                    }
            )
        }

        Text(
            text = title,
            style = MRTheme.typography.header,
            modifier = Modifier.align(Alignment.Center).animateContentSize(tween(durationMillis = 600))
        )

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimationVisibility(
                visibility = onLeftIconActionClick != null,
                offsetX = 300
            ) {
                Image(
                    imageVector = actionLeftIcon.icon,

                    colorFilter = ColorFilter.tint(MRTheme.colors.backgroundOn),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(16.dp)
                        .clickableRipple {
                            onLeftIconActionClick?.invoke()
                        }
                )
            }
            AnimationVisibility(
                visibility = onRightIconActionClick != null,
                offsetX = 300
            ) {
                Image(
                    imageVector = actionRightIcon.icon,
                    colorFilter = ColorFilter.tint(MRTheme.colors.backgroundOn),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickableRipple {
                            onRightIconActionClick?.invoke()
                        }
                )
            }
        }
    }
}

enum class ToolbarBackIcon(val icon: ImageVector) {
    Back(
        icon = Icons.Default.ArrowBack
    ),
    Close(
        icon = Icons.Default.Close
    );
}

enum class ToolbarActionIcon(val icon: ImageVector) {
    Add(
        icon = Icons.Default.Add
    ),
    Edit(
        icon = Icons.Default.Edit,
    );
}
