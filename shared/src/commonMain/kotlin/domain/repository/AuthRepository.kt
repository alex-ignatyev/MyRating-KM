package domain.repository

import data.remote.RemoteAuthDataSource
import data.remote.SettingsDataSource
import domain.mapper.toDomain
import model.data.auth.request.ForgotRequest
import model.data.auth.request.LoginRequest
import model.data.auth.request.PasswordRequest
import model.data.auth.request.RegisterRequest
import model.domain.User
import utils.answer.Answer
import utils.answer.map

class AuthRepositoryImpl(
    private val remote: RemoteAuthDataSource,
    private val settings: SettingsDataSource
) : AuthRepository {

    override suspend fun getUserInfo(): Answer<User> {
        return remote.getUserInfo(login = settings.getUserLogin()).map { it.toDomain() }
    }

    override suspend fun register(
        login: String,
        password: String,
        repeatPassword: String,
        email: String,
        phone: String
    ): Answer<Unit> {
        return remote.register(
            RegisterRequest(
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
    ): Answer<String> {
        return remote.login(
            LoginRequest(
                login = login,
                password = password
            )
        ).map { it.login.orEmpty() }
    }

    override suspend fun forgot(
        email: String,
        newPassword: String,
        repeatNewPassword: String
    ): Answer<Unit> {
        return remote.forgot(
            ForgotRequest(
                email = email,
                newPassword = newPassword,
                repeatNewPassword = repeatNewPassword
            )
        )
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatNewPassword: String
    ): Answer<Unit> {
        return remote.changePassword(
            login = settings.getUserLogin(),
            request = PasswordRequest(
                currentPassword = currentPassword,
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
    ): Answer<String>

    suspend fun forgot(
        email: String,
        newPassword: String,
        repeatNewPassword: String
    ): Answer<Unit>

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatNewPassword: String
    ): Answer<Unit>
}
