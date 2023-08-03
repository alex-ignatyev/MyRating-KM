package domain.repository

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

    override suspend fun getTobaccoFeed(search: String): Answer<List<TobaccoFeed>> {
        return remote.getTobaccoFeed(search).map { it.toDomain() }
    }

    override suspend fun getTobaccoInfo(tobaccoId: String): Answer<TobaccoInfo> {
        return remote.postTobaccoInfo(TobaccoInfoRequest(tobaccoId)).map { it.toDomain() }
    }

    override suspend fun postTobaccoVote(
        tobaccoId: String,
        type: VoteType,
        value: Long
    ): Answer<Unit> {
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
