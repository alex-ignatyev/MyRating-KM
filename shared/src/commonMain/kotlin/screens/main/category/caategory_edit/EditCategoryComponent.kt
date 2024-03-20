package screens.main.category.caategory_edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import domain.repository.CategoryRepository
import kotlinx.coroutines.launch
import model.domain.Category
import org.koin.core.component.inject
import screens.main.category.caategory_edit.EditCategoryAction.ChangeCategory
import screens.main.category.caategory_edit.EditCategoryAction.InitEditCategoryScreen
import screens.main.category.caategory_edit.EditCategoryAction.OnBackClick
import screens.main.category.caategory_edit.EditCategoryAction.OnCategoryIconChange
import screens.main.category.caategory_edit.EditCategoryAction.OnCategoryTitleChange
import utils.BaseComponent
import utils.answer.onFailure
import utils.answer.onSuccess

class DefaultEditCategoryComponent(
    componentContext: ComponentContext,
    private val category: Category,
    private val returnToPreviousScreen: () -> Unit
) : EditCategoryComponent, BaseComponent<EditCategoryEffect>(componentContext) {

    val repository: CategoryRepository by inject()

    override val state = MutableValue(EditCategoryState())

    override fun doAction(action: EditCategoryAction) {
        when (action) {
            is InitEditCategoryScreen -> fetchData()
            is OnCategoryTitleChange -> changeCategoryTitle(action.value)
            is OnCategoryIconChange -> changeCategoryIcon(action.value)
            is ChangeCategory -> changeCategory()
            is OnBackClick -> returnToPreviousScreen.invoke()
        }
    }

    private fun fetchData() {
        state.value = state.value.copy(title = category.title, icon = category.icon)
    }

    private fun changeCategoryTitle(title: String) {
        state.value = state.value.copy(title = title)
    }

    private fun changeCategoryIcon(icon: Int) {
        state.value = state.value.copy(icon = icon)
    }

    private fun changeCategory() {
        if (canEditCategory()) return
        componentScope.launch {
            repository.updateCategory(
                categoryId = category.id,
                title = state.value.title.trim(),
                icon = state.value.icon
            ).onSuccess {
                returnToPreviousScreen.invoke()
            }.onFailure {
                state.value = state.value.copy(error = it.message)
            }
        }
    }

    private fun canEditCategory(): Boolean {
        if (state.value.title.length < 3) {
            state.value = state.value.copy(error = "Category title length should be more 2 symbols")
            return true
        }
        return false
    }
}

interface EditCategoryComponent {
    val state: Value<EditCategoryState>
    fun doAction(action: EditCategoryAction)
}
