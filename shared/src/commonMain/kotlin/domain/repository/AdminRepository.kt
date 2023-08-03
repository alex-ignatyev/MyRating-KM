package domain.repository

import data.RemoteAdminDataSource
import data.SettingsDataSource
import model.data.admin.AdminAddTobaccoRequest
import model.domain.Company
import model.domain.toDomain
import utils.answer.Answer
import utils.answer.map

class AdminRepositoryImpl(
    private val remote: RemoteAdminDataSource,
    private val settings: SettingsDataSource
) : AdminRepository {

    override suspend fun addTobacco(
        taste: String,
        company: String,
        line: String,
        strength: Long
    ): Answer<Unit> {
        return remote.addTobacco(
            settings.getToken(), AdminAddTobaccoRequest(
                taste = taste,
                company = company,
                line = line,
                strength = strength
            )
        )
    }

    override suspend fun getCompanies(): Answer<List<Company>> {
        return remote.getCompanies(settings.getToken()).map { it.toDomain() }
    }

}

interface AdminRepository {
    suspend fun addTobacco(
        taste: String,
        company: String,
        line: String,
        strength: Long
    ): Answer<Unit>

    suspend fun getCompanies(): Answer<List<Company>>
}