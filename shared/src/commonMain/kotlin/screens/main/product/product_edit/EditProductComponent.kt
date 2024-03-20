package screens.main.product.product_edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.ProductRepository
import kotlinx.coroutines.launch
import model.data.product.request.UpdateProductRequest
import model.domain.Product
import org.koin.core.component.inject
import screens.main.product.product_edit.EditProductAction.ChangeProduct
import screens.main.product.product_edit.EditProductAction.ChangeRateProduct
import screens.main.product.product_edit.EditProductAction.ChangeTitleProduct
import screens.main.product.product_edit.EditProductAction.InitEditProductScreen
import screens.main.product.product_edit.EditProductAction.OnBackClick
import utils.BaseComponent
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultEditProductComponent(
    component: ComponentContext,
    private val categoryId: Long,
    private val product: Product,
    private val returnToPreviousScreen: () -> Unit
) : EditProductComponent, BaseComponent<EditProductEffect>(component) {

    val repository: ProductRepository by inject()

    override val state = MutableValue(EditProductState())

    override fun doAction(action: EditProductAction) {
        when (action) {
            is InitEditProductScreen -> fetchData()
            is OnBackClick -> returnToPreviousScreen.invoke()
            is ChangeTitleProduct -> changeTitleProduct(action.title)
            is ChangeRateProduct -> changeRateProduct(action.rate)
            is ChangeProduct -> changeProduct()
        }
    }

    private fun fetchData() {
        state.value = state.value.copy(title = product.title, rate = product.rate.toString())
    }

    private fun changeTitleProduct(title: String) {
        state.value = state.value.copy(title = title)

    }

    private fun changeRateProduct(rate: String) {
        state.value = state.value.copy(rate = rate)
    }

    private fun changeProduct() {
        if (canUpdateProduct()) return
        componentScope.launch {
            repository.updateProduct(
                UpdateProductRequest(
                    categoryId = categoryId,
                    productId = product.id,
                    newTitle = state.value.title.trim(),
                    newRate = state.value.rate.toInt()
                )
            ).onSuccess {
                returnToPreviousScreen.invoke()
            }.onFailure {
                state.value = state.value.copy(error = it.message)
            }
        }
    }

    private fun canUpdateProduct(): Boolean {
        if (state.value.title.length < 3) {
            state.value = state.value.copy(error = "Title should be more then 2 symbols")
            return true
        }

        if (state.value.rate.isEmpty()) {
            state.value = state.value.copy(error = "Rate can't be empty")
            return true
        }

        if (state.value.rate.toInt() > 10 || state.value.rate.toInt() < 1) {
            state.value = state.value.copy(error = "Rate should be 1-10")
            return true
        }
        return false
    }
}

interface EditProductComponent {
    val state: Value<EditProductState>
    fun doAction(action: EditProductAction)
}
