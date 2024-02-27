package domain.repository

import data.remote.RemoteAuthDataSource
import data.remote.SettingsDataSource
import model.data.auth.request.AccountCreateRequest
import model.data.auth.request.AccountForgotRequest
import model.data.auth.request.AccountLoginRequest
import model.domain.User
import utils.answer.Answer

class AuthRepositoryImpl(
    private val remote: RemoteAuthDataSource,
    private val settings: SettingsDataSource
) : AuthRepository {

    override suspend fun getUserInfo(): Answer<User> {
        return remote.getUserInfo(settings.getUserLogin())
    }

    override suspend fun register(
        login: String,
        password: String,
        repeatPassword: String,
        email: String,
        phone: String
    ): Answer<Unit> {
        return remote.register(
            AccountCreateRequest(
                login = login,
                password = password,
                repeatPassword = repeatPassword,
                email = email,
                phone = phone
            )
        )
    }

    override suspend fun login(
        login: String,
        password: String
    ): Answer<Unit> {
        return remote.login(
            AccountLoginRequest(
                login = login,
                password = password
            )
        )
    }

    override suspend fun forgot(
        email: String,
        newPassword: String,
        repeatNewPassword: String
    ): Answer<Unit> {
        return remote.forgotPassword(
            AccountForgotRequest(
                email = email,
                newPassword = newPassword,
                repeatNewPassword = repeatNewPassword
            )
        )
    }
}

interface AuthRepository {
    suspend fun getUserInfo(): Answer<User>

    suspend fun register(
        login: String,
        password: String,
        repeatPassword: String,
        email: String,
        phone: String
    ): Answer<Unit>

    suspend fun login(
        login: String,
        password: String
    ): Answer<Unit>

    suspend fun forgot(
        email: String,
        newPassword: String,
        repeatNewPassword: String
    ): Answer<Unit>
}
