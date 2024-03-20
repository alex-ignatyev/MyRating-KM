package screens.main.category.category_add

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.CategoryRepository
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import screens.main.category.category_add.AddCategoryAction.AddCategory
import screens.main.category.category_add.AddCategoryAction.OnBackClick
import screens.main.category.category_add.AddCategoryAction.OnCategoryChange
import screens.main.category.category_add.AddCategoryAction.OnCategoryIconChange
import utils.BaseComponent
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultAddCategoryComponent(
    componentContext: ComponentContext,
    private val returnToPreviousScreen: () -> Unit
) : AddCategoryComponent, BaseComponent<AddCategoryEffect>(componentContext) {

    private val repository: CategoryRepository by inject()

    override val state = MutableValue(AddCategoryState())

    override fun doAction(action: AddCategoryAction) {
        when (action) {
            is OnBackClick -> returnToPreviousScreen.invoke()
            is OnCategoryChange -> changeCategory(action.value)
            is OnCategoryIconChange -> changeCategoryIcon(action.value)
            is AddCategory -> addCategory()
        }
    }

    private fun changeCategory(title: String) {
        state.value = state.value.copy(title = title)
    }

    private fun changeCategoryIcon(icon: Int) {
        state.value = state.value.copy(icon = icon)
    }

    private fun addCategory() {
        if (canAddCategory()) return
        state.value = state.value.copy(isLoading = true)
        componentScope.launch {
            repository.addCategory(
                title = state.value.title.trim(),
                icon = state.value.icon
            ).onSuccess {
                returnToPreviousScreen.invoke()
            }.onFailure {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun canAddCategory(): Boolean {
        if (state.value.title.length < 3) {
            state.value = state.value.copy(error = "Category title should be more than 3 symbols")
            return true
        }
        return false
    }
}

interface AddCategoryComponent {
    val state: Value<AddCategoryState>
    fun doAction(action: AddCategoryAction)
}
