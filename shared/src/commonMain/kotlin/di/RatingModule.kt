package di

import data.remote.RemoteCategoriesDataSource
import data.remote.RemoteProductsDataSource
import domain.repository.CategoryRepository
import domain.repository.CategoryRepositoryImpl
import domain.repository.ProductRepository
import domain.repository.ProductRepositoryImpl
import org.koin.dsl.module

val ratingModule = module {
    single { RemoteCategoriesDataSource(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }

    single { RemoteProductsDataSource(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
}
