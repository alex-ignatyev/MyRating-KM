package domain.repository

import com.my_rating.shared.BuildKonfig
import data.RemoteMainDataSource
import model.data.tobacco.TobaccoInfoRequest
import model.data.tobacco.TobaccoVoteRequest
import model.data.tobacco.TobaccoVoteRequest.VoteType
import model.domain.TobaccoFeed
import model.domain.TobaccoInfo
import model.domain.toDomain
import utils.answer.Answer
import utils.answer.map


class RatingRepositoryImpl(
    private val remote: RemoteMainDataSource
) : RatingRepository {

    private val isMocked = BuildKonfig.isMocked

    override suspend fun getTobaccoFeed(search: String): Answer<List<TobaccoFeed>> {
        if (isMocked) return Answer.success(listOf<TobaccoFeed>()) //TODO
        return remote.getTobaccoFeed(search).map { it.toDomain() }
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
    suspend fun getTobaccoFeed(search: String): Answer<List<TobaccoFeed>>
    suspend fun getTobaccoInfo(tobaccoId: String): Answer<TobaccoInfo>
    suspend fun postTobaccoVote(
        tobaccoId: String,
        type: VoteType,
        value: Long
    ): Answer<Unit>
}
