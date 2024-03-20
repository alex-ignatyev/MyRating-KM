package domain.repository

import com.my_rating.shared.BuildKonfig
import data.remote.RemoteCategoriesDataSource
import data.remote.SettingsDataSource
import domain.mapper.toDomain
import model.data.category.request.AddCategoryRequest
import model.domain.Category
import utils.answer.Answer
import utils.answer.map

class CategoryRepositoryImpl(
    private val remote: RemoteCategoriesDataSource,
    private val settings: SettingsDataSource
) : CategoryRepository {

    private val isMocked = BuildKonfig.isMocked

    override suspend fun getCategories(): Answer<List<Category>> {
        return remote.getCategories(login = settings.getUserLogin()).map { it.toDomain() }
    }

    override suspend fun addCategory(title: String, icon: Int): Answer<Unit> {
        return remote.addCategory(login = settings.getUserLogin(), request = AddCategoryRequest(title, icon))
    }

    override suspend fun updateCategory(title: String, icon: Int): Answer<Unit> {
        TODO("Not yet implemented")
    }
}

interface CategoryRepository {
    suspend fun getCategories(): Answer<List<Category>>
    suspend fun addCategory(title: String, icon: Int): Answer<Unit>
    suspend fun updateCategory(title: String, icon: Int): Answer<Unit>
}
