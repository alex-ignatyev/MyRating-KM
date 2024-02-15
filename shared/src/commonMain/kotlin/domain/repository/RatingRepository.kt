package domain.repository

import com.my_rating.shared.BuildKonfig
import data.remote.RemoteMainDataSource
import model.domain.Category
import utils.answer.Answer

class RatingRepositoryImpl(
    private val remote: RemoteMainDataSource
) : RatingRepository {

    private val isMocked = BuildKonfig.isMocked

    override suspend fun getCategories(search: String): Answer<List<Category>> {
        return Answer.success(
            listOf<Category>(
                Category(
                    id = "0",
                    image = "",
                    title = "Напиток",
                    subcategories = listOf(
                        Category(
                            id = "1",
                            title = "Газировка",
                            image = "",
                            subcategories = listOf(
                                Category(
                                    id = "2",
                                    title = "Cola",
                                    image = "",
                                    subcategories = listOf(

                                    )
                                )
                            )
                        )
                    )
                ),
                Category(
                    id = "",
                    image = "",
                    title = "222",
                    subcategories = listOf()
                ),
                Category(
                    id = "",
                    image = "",
                    title = "333",
                    subcategories = listOf()
                ),
                Category(
                    id = "",
                    image = "",
                    title = "444",
                    subcategories = listOf()
                )
            )
        )
        // return remote.getTobaccoFeed(search).map { it.toDomain() }
    }
}

interface RatingRepository {
    suspend fun getCategories(search: String): Answer<List<Category>>
}
