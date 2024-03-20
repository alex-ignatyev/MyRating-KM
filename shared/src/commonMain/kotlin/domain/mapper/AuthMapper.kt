package domain.mapper

import model.data.auth.response.UserResponse
import model.domain.User
import utils.orZero

fun UserResponse.toDomain(): User {
    return User(
        id = id.orZero(),
        login = login.orEmpty(),
        email = email.orEmpty(),
        phone = phone.orEmpty()
    )
}
