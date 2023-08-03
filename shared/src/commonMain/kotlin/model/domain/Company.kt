package model.domain

import model.data.admin.CompanyResponse

data class Company(
    val id: String,
    val company: String,
    val lines: List<String>
)

fun CompanyResponse.toDomain(): Company {
    return Company(
        id = id.orEmpty(),
        company = company.orEmpty(),
        lines = lines ?: emptyList()
    )
}

fun List<CompanyResponse>.toDomain(): List<Company> {
    return this.map { it.toDomain() }
}
