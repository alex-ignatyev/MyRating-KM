package screens.main.product.product_add

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
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.my_rating.shared.AppRes
import com.my_rating.shared.strings.AppResStrings
import screens.main.product.product_add.AddProductAction.AddProduct
import screens.main.product.product_add.AddProductAction.OnBackClick
import screens.main.product.product_add.AddProductAction.OnProductRateChange
import screens.main.product.product_add.AddProductAction.OnProductTitleChange
import ui.MRTheme
import ui.components.MRButton
import ui.components.MRCircularProgress
import ui.components.MRTextError
import ui.components.MRTextField
import ui.components.MRToolbar
import ui.components.ToolbarBackIcon.Close
import utils.keyboardAsState

@Composable
fun AddProductView(
    state: AddProductState,
    rootModifier: Modifier = Modifier,
    doAction: (AddProductAction) -> Unit
) {

    val isKeyboardOpen by keyboardAsState()
    val buttonModifier = if (isKeyboardOpen) Modifier.windowInsetsPadding(WindowInsets.ime) else rootModifier

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            MRToolbar(
                title = AppResStrings.title_add_product,
                backIcon = Close,
                onBackClick = {
                    doAction.invoke(OnBackClick)
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
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
                    isError = state.error.isNotEmpty(),
                ) {
                    doAction(OnProductTitleChange(it))
                }

                MRTextField(
                    value = state.rate,
                    placeholder = AppResStrings.text_products_rate,
                    isError = state.error.isNotEmpty(),
                    inputType = KeyboardType.Phone,
                    onValueChange = { newRate ->
                        doAction(OnProductRateChange(newRate))
                    }
                )

                MRTextError(errorText = state.error)
            }

            MRButton(
                modifier = buttonModifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.BottomCenter),
                text = if (state.isLoading) null else AppRes.string.title_add_product,
                enabled = !state.isLoading,
                content = {
                    MRCircularProgress()
                },
                onClick = {
                    doAction(AddProduct)
                }
            )
        }
    }
}
