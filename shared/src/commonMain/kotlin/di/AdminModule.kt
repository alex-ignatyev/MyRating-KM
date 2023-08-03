package di

import data.RemoteAdminDataSource
import domain.repository.AdminRepository
import domain.repository.AdminRepositoryImpl
import org.koin.dsl.module

val adminModule = module {
    single { RemoteAdminDataSource(get()) }
    single<AdminRepository> { AdminRepositoryImpl(get(), get()) }
}
