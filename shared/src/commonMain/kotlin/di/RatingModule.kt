package di

import data.remote.RemoteMainDataSource
import domain.repository.RatingRepository
import domain.repository.RatingRepositoryImpl
import org.koin.dsl.module

val ratingModule = module {
    single { RemoteMainDataSource(get()) }
    single<RatingRepository> { RatingRepositoryImpl(get()) }
}
