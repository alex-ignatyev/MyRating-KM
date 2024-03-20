package screens.main.product.product_edit

sealed interface EditProductAction {
    data object InitEditProductScreen : EditProductAction
    data object OnBackClick : EditProductAction
    data class ChangeTitleProduct(
        val title: String
    ) : EditProductAction

    data class ChangeRateProduct(
        val rate: String
    ) : EditProductAction

    data object ChangeProduct : EditProductAction
}

data class EditProductState(
    val isLoading: Boolean = false,
    val title: String = "",
    val rate: String = "",
    val error: String = ""
)

sealed interface EditProductEffect
