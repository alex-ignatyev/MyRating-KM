package screens.main.product.product_add

import androidx.compose.ui.text.toUpperCase
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.ProductRepository
import kotlinx.coroutines.launch
import model.data.product.request.AddProductRequest
import org.koin.core.component.inject
import screens.main.product.product_add.AddProductAction.AddProduct
import screens.main.product.product_add.AddProductAction.OnBackClick
import screens.main.product.product_add.AddProductAction.OnProductRateChange
import screens.main.product.product_add.AddProductAction.OnProductTitleChange
import utils.BaseComponent
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultAddProductComponent(
    componentContext: ComponentContext,
    private val categoryId: Long,
    private val returnToPreviousScreen: () -> Unit
) : AddProductComponent, BaseComponent<AddProductEffect>(componentContext) {

    private val repository: ProductRepository by inject()
    override val state = MutableValue(AddProductState())

    override fun doAction(action: AddProductAction) {
        when (action) {
            is OnProductTitleChange -> changeTitle(action.value)
            is OnProductRateChange -> changeRate(action.value)
            is AddProduct -> addProduct()
            is OnBackClick -> returnToPreviousScreen.invoke()
        }
    }

    private fun changeTitle(title: String) {
        state.value = state.value.copy(title = title)
    }

    private fun changeRate(rate: String) {
        state.value = state.value.copy(rate = rate)
    }

    private fun addProduct() {
        if (canAddProduct()) return
        state.value = state.value.copy(isLoading = true)
        val request = AddProductRequest(categoryId, state.value.title, state.value.rate.toInt())
        componentScope.launch {
            repository.addProduct(request).onSuccess {
                returnToPreviousScreen.invoke()
            }.onFailure {
                state.value = state.value.copy(error = it.message, isLoading = false)
            }
        }
    }

    private fun canAddProduct(): Boolean {
        if(state.value.title.isEmpty()) {
            state.value = state.value.copy(error = "Empty")
            return true
        }

        if(state.value.title.length < 4) {
            state.value = state.value.copy(error = "Product title should be more than 3 symbols")
            return true
        }

        if (!state.value.rate.matches(Regex("""\b([1-9]|10)\b"""))) {
            state.value = state.value.copy(error = "Rate should be 1-10")
            return true
        }

        return false
    }
}

interface AddProductComponent {
    val state: Value<AddProductState>
    fun doAction(action: AddProductAction)
}
