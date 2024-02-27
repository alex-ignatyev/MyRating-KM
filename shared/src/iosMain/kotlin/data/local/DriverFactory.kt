package data.local

import Database
import Database.Companion
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import di.PlatformConfiguration

actual class DriverFactory actual constructor(private val platformConfiguration: PlatformConfiguration) {
    actual fun createDriver(name: String): SqlDriver {
        return NativeSqliteDriver(Database.Schema, name)
    }
}
