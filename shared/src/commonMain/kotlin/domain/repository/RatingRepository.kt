package domain.repository

import com.my_rating.shared.BuildKonfig
import data.remote.RemoteCategoriesDataSource
import model.domain.Category
import utils.answer.Answer
import utils.answer.map

class RatingRepositoryImpl(
    private val remote: RemoteCategoriesDataSource
) : RatingRepository {

    private val isMocked = BuildKonfig.isMocked

    override suspend fun getCategories(): Answer<List<Category>> {
        return remote.getUserInfo("root").map { it.categories }
    }
}

interface RatingRepository {
    suspend fun getCategories(): Answer<List<Category>>
}
