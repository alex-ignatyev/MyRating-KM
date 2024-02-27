package di

import data.remote.RemoteCategoriesDataSource
import domain.repository.RatingRepository
import domain.repository.RatingRepositoryImpl
import org.koin.dsl.module

val ratingModule = module {
    single { RemoteCategoriesDataSource(get()) }
    single<RatingRepository> { RatingRepositoryImpl(get()) }
}
