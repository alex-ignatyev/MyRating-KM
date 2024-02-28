package screens.main.product.product_add

sealed interface AddProductAction {
    data class OnProductTitleChange(val value: String) : AddProductAction
    data class OnProductRateChange(val value: String) : AddProductAction
    data object AddProduct : AddProductAction
    data object OnBackClick : AddProductAction
}

data class AddProductState(
    val title: String = "",
    val rate: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)

sealed interface AddProductEffect