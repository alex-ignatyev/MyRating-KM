package di

import Database
import database.DriverFactory
import org.koin.dsl.module

val databaseModule = module {

    single { DriverFactory(get()) }

    single {
        val driverFactory: DriverFactory = get()
        val driver = driverFactory.createDriver("lm_customer.db") //TODO что это ?

        Database(driver)
    }
}
