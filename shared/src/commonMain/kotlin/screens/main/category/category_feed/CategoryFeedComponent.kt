package screens.main.category.category_feed

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.CategoryRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.main.category.category_feed.CategoryFeedAction.AddCategory
import screens.main.category.category_feed.CategoryFeedAction.InitCategoryFeedScreen
import screens.main.category.category_feed.CategoryFeedAction.OnCategoryClick
import screens.main.category.category_feed.CategoryFeedAction.OnDataRefresh
import screens.main.category.category_feed.CategoryFeedAction.OnErrorRefresh
import utils.BaseComponent
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultCategoryFeedComponent(
    componentContext: ComponentContext,
    val openCategoryScreen: (Long, String) -> Unit,
    val openAddCategoryScreen: () -> Unit
) : CategoryFeedComponent, BaseComponent<CategoryFeedEffect>(componentContext) {

    private val repository: CategoryRepository by inject()

    override val state = MutableValue(CategoryFeedState())

    /* init {
            backHandler.register(BackCallback {
                toPrevious()
            })
     } */

    override fun doAction(action: CategoryFeedAction) {
        when (action) {
            is InitCategoryFeedScreen -> fetchData()
            is AddCategory -> openAddCategoryScreen()
            is OnDataRefresh, is OnErrorRefresh -> fetchData()
            is OnCategoryClick -> openCategoryScreen(action.category.id, action.category.title)
        }
    }

    private fun fetchData() {
        state.value = state.value.copy(isLoading = true)
        componentScope.launch {
            repository.getCategories().onSuccess { response ->
                state.value = state.value.copy(data = response, isLoading = false)
            }.onFailure {
                state.value = state.value.copy(error = it.message, isLoading = false)
            }
        }
    }
}

interface CategoryFeedComponent {
    val state: Value<CategoryFeedState>
    fun doAction(action: CategoryFeedAction)
}
