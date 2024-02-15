package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.compose.ui.unit.dp
import com.my_rating.shared.strings.AppResStrings
import kotlinx.coroutines.delay
import ui.MRTheme
import utils.EMPTY

@Composable
fun MRSearch(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    var inputText by remember { mutableStateOf(EMPTY) }
    var firstInit by remember { mutableStateOf(true) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = inputText,
            onValueChange = {
                inputText = it
            },
            textStyle = MRTheme.typography.hint.copy(textAlign = Companion.Start),
            modifier = Modifier
                .background(color = MRTheme.colors.surfaceVariant.copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
                .weight(1f)
                .height(48.dp),
            placeholder = { Text(text = AppResStrings.text_search, style = MRTheme.typography.hint) },
            leadingIcon = {
                Image(
                    imageVector = Icons.Outlined.Search,
                    colorFilter = ColorFilter.tint(MRTheme.colors.primaryText.copy(alpha = 0.5f)),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (inputText.isNotBlank()) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp).clickable {
                        inputText = EMPTY
                    })
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                textColor = MRTheme.colors.primaryText,
                cursorColor = MRTheme.colors.surfaceVariantOn
            )
        )

        //TODO сделать фильтр адаптивным
        /*
        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(46.dp)
                .border(
                    border = BorderStroke(1.dp, MRTheme.colors.surfaceVariant.copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(8.dp)
                ).clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(AppResImages.ic_filter), contentDescription = null, modifier = Modifier.size(16.dp))
        }
        */
    }

    LaunchedEffect(inputText) {
        if (firstInit) {
            firstInit = false
            return@LaunchedEffect
        }
        delay(500L)
        onValueChange(inputText)
    }
}
