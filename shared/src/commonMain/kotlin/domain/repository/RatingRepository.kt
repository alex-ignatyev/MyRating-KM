package domain.repository

import com.my_rating.shared.BuildKonfig
import data.RemoteMainDataSource
import model.data.tobacco.TobaccoInfoRequest
import model.data.tobacco.TobaccoVoteRequest
import model.data.tobacco.TobaccoVoteRequest.VoteType
import model.domain.Category
import model.domain.TobaccoInfo
import model.domain.toDomain
import utils.answer.Answer
import utils.answer.map


class RatingRepositoryImpl(
    private val remote: RemoteMainDataSource
) : RatingRepository {

    private val isMocked = BuildKonfig.isMocked

    override suspend fun getFeed(search: String): Answer<List<Category>> {
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

    override suspend fun getTobaccoInfo(tobaccoId: String): Answer<TobaccoInfo> {
        if (isMocked) return Answer.success(
            TobaccoInfo(
                id = "",
                image = "",
                taste = "",
                company = "",
                line = "",
                strength = 0,
                commentsSize = 0,
                strengthByUsers = 0.0f,
                smokinessByUsers = 0.0f,
                aromaByUsers = 0.0f,
                ratingByUsers = 0.0f,
                tasteByUsers = 0.0f,
                ratingByUser = 0,
                strengthByUser = 0,
                smokinessByUser = 0,
                aromaByUser = 0,
                tasteByUser = 0,
                votes = 0
            )
        ) //TODO
        return remote.postTobaccoInfo(TobaccoInfoRequest(tobaccoId)).map { it.toDomain() }
    }

    override suspend fun postTobaccoVote(
        tobaccoId: String,
        type: VoteType,
        value: Long
    ): Answer<Unit> {
        if (isMocked) return Answer.success(Unit) //TODO
        return remote.postTobaccoVote(
            TobaccoVoteRequest(
                tobaccoId = tobaccoId,
                type = type,
                value = value
            )
        )
    }
}

interface RatingRepository {
    suspend fun getFeed(search: String): Answer<List<Category>>
    suspend fun getTobaccoInfo(tobaccoId: String): Answer<TobaccoInfo>
    suspend fun postTobaccoVote(
        tobaccoId: String,
        type: VoteType,
        value: Long
    ): Answer<Unit>
}
