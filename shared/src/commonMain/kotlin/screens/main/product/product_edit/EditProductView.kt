package screens.main.product.product_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.my_rating.shared.strings.AppResStrings
import screens.main.product.product_edit.EditProductAction.ChangeProduct
import screens.main.product.product_edit.EditProductAction.ChangeRateProduct
import screens.main.product.product_edit.EditProductAction.ChangeTitleProduct
import screens.main.product.product_edit.EditProductAction.OnBackClick
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRTextError
import ui.components.MRTextField
import ui.components.MRToolbar
import ui.components.ToolbarBackIcon.Close
import utils.keyboardAsState

@Composable
fun EditProductView(
    state: EditProductState,
    rootModifier: Modifier = Modifier,
    doAction: (EditProductAction) -> Unit
) {

    val isKeyboardOpen by keyboardAsState()
    val buttonModifier = if (isKeyboardOpen) Modifier.windowInsetsPadding(WindowInsets.ime) else rootModifier

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            MRToolbar(
                title = AppResStrings.title_edit_product,
                backIcon = Close,
                onBackClick = {
                    doAction.invoke(OnBackClick)
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MRTheme.colors.background)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                MRTextField(
                    value = state.title,
                    placeholder = AppResStrings.text_products_name,
                    onValueChange = {
                        doAction(ChangeTitleProduct(it))
                    }
                )
                MRTextField(
                    value = state.rate,
                    placeholder = AppResStrings.text_products_rate,
                    inputType = KeyboardType.Phone,
                    onValueChange = { rate ->
                        doAction(ChangeRateProduct(rate))
                    }
                )

                MRTextError(errorText = state.error)
            }

            MRButton(
                modifier = buttonModifier
                    .align(Alignment.BottomCenter),
                text = AppResStrings.change_product,
                onClick = {
                    doAction(ChangeProduct)
                }
            )
        }
    }
}
