package model.domain

import ktor.getBaseUrl
import model.data.tobacco.TobaccoInfoResponse
import utils.orZero

data class TobaccoInfo(
    val id: String,
    var image: String,
    val taste: String,
    val company: String,
    val line: String,
    val strength: Int,

    val commentsSize: Int,

    val strengthByUsers: Float,
    val smokinessByUsers: Float,
    val aromaByUsers: Float,
    val ratingByUsers: Float,
    val tasteByUsers: Float,

    val ratingByUser: Long,
    val strengthByUser: Long,
    val smokinessByUser: Long,
    val aromaByUser: Long,
    val tasteByUser: Long,

    val votes: Long
) {

    companion object {
        val EMPTY = TobaccoInfo(
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
    }
}

fun TobaccoInfoResponse.toDomain(): TobaccoInfo {
    return TobaccoInfo(
        id = id.orEmpty(),
        image = getBaseUrl() + image.orEmpty(),
        taste = taste.orEmpty(),
        company = company.orEmpty(),
        line = line.orEmpty(),
        strength = strength.orZero(),

        commentsSize = 0, //TODO

        strengthByUsers = strengthByUsers.orZero(),
        smokinessByUsers = smokinessByUsers.orZero(),
        aromaByUsers = aromaByUsers.orZero(),
        ratingByUsers = ratingByUsers.orZero(),
        tasteByUsers = tasteByUsers.orZero(),
        ratingByUser = ratingByUser.orZero(),
        strengthByUser = strengthByUser.orZero(),
        smokinessByUser = smokinessByUser.orZero(),
        aromaByUser = aromaByUser.orZero(),
        tasteByUser = tasteByUser.orZero(),
        votes = votes.orZero()
    )
}
