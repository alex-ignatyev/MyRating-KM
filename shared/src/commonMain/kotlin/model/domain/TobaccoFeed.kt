package model.domain

import ktor.getBaseUrl
import model.data.tobacco.TobaccoFeedResponse
import utils.orZero

data class TobaccoFeed(
    val id: String,
    val taste: String,
    val company: String,
    val line: String,
    var image: String,
    val rating: Float,
    val votes: Long
)

fun TobaccoFeedResponse.toDomain(): TobaccoFeed {
    return TobaccoFeed(
        id = id.orEmpty(),
        taste = taste.orEmpty(),
        company = company.orEmpty(),
        line = line.orEmpty(),
        image = getBaseUrl() + image.orEmpty(),
        rating = rating.orZero(),
        votes = votes.orZero()
    )
}

fun List<TobaccoFeedResponse>.toDomain(): List<TobaccoFeed> {
    return this.map { it.toDomain() }
}
