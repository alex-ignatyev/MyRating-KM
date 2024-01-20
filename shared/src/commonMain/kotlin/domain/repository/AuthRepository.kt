package domain.repository

import com.my_rating.shared.AppRes
import com.my_rating.shared.BuildKonfig
import data.RemoteAuthDataSource
import data.SettingsDataSource
import model.data.auth.request.AccountCreateRequest
import model.data.auth.request.AccountForgotRequest
import model.data.auth.request.AccountLoginRequest
import utils.answer.Answer
import utils.answer.ErrorCode.InternalError
import utils.answer.onFailure
import utils.answer.onSuccess

class AuthRepositoryImpl(
    private val remote: RemoteAuthDataSource,
    private val settings: SettingsDataSource
) : AuthRepository {

    private val isMocked = BuildKonfig.isMocked

    override suspend fun authorize(): Answer<Unit> {
        if (isMocked) return Answer.success(Unit) //TODO
        return remote.authorize(settings.getToken())
    }

    override suspend fun create(
        login: String,
        name: String,
        password: String,
        repeatPassword: String
    ): Answer<Unit> {
        if (isMocked) return Answer.success(Unit) //TODO
        return remote.createAccount(
            AccountCreateRequest(
                login = login,
                name = name,
                password = password,
                repeatPassword = repeatPassword
            )
        )
    }

    override suspend fun login(
        login: String,
        password: String
    ): Answer<Unit> {
        if (isMocked) return Answer.success(Unit) //TODO
        remote.login(
            AccountLoginRequest(
                login = login, password = password
            )
        ).onSuccess {
            if (it.userId == null || it.token == null || it.isAdmin == null) {
                return Answer.failure(code = InternalError, message = AppRes.string.error_something_went_wrong)
            }
            settings.saveInfo(it.token, it.userId, it.isAdmin)
        }.onFailure {
            return Answer.failure(it.code, it.message)
        }
        return Answer.success(Unit)
    }

    override suspend fun forgot(
        login: String,
        newPassword: String,
        repeatNewPassword: String
    ): Answer<Unit> {
        if (isMocked) return Answer.success(Unit) //TODO
        return remote.forgotPassword(
            AccountForgotRequest(
                login = login,
                newPassword = newPassword,
                repeatNewPassword = repeatNewPassword
            )
        )
    }
}

interface AuthRepository {
    suspend fun authorize(): Answer<Unit>
    suspend fun create(
        login: String,
        name: String,
        password: String,
        repeatPassword: String
    ): Answer<Unit>

    suspend fun login(
        login: String,
        password: String
    ): Answer<Unit>

    suspend fun forgot(
        login: String,
        newPassword: String,
        repeatNewPassword: String
    ): Answer<Unit>
}
