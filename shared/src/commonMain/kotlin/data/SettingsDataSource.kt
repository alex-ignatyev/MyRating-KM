package data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

class SettingsDataSource(
    private val settings: Settings
) {

    fun saveInfo(token: String, userId: String, isAdmin: Boolean) {
        settings.putString(TOKEN_KEY, "Bearer $token")
        settings.putString(USERID_KEY, userId)
        settings.putBoolean(ADMIN_KEY, isAdmin)
    }

    var isDarkMode: Boolean
        get() = settings[DARKMODE_KEY, false]
        set(value) = settings.putBoolean(DARKMODE_KEY, value)

    fun getToken(): String {
        return settings[TOKEN_KEY, ""]
    }

    fun getUserId(): String {
        return settings[USERID_KEY, ""]
    }

    fun getAdmin(): Boolean {
        return settings[ADMIN_KEY, false]
    }

    fun clear() {
        settings.clear()
    }

    companion object {
        private const val TOKEN_KEY = "tokenKey"
        private const val USERID_KEY = "userIdKey"
        private const val ADMIN_KEY = "adminKey"
        private const val DARKMODE_KEY = "darkmodeKey"
    }
}
