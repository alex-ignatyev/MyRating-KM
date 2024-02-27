package di

import data.remote.RemoteAuthDataSource
import domain.repository.AuthRepository
import domain.repository.AuthRepositoryImpl
import org.koin.dsl.module

val authModule = module {
    single { RemoteAuthDataSource(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}
