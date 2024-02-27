package data.remote

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

class SettingsDataSource(
    private val settings: Settings
) {

    var isDarkMode: Boolean
        get() = settings[DARKMODE_KEY, false]
        set(value) = settings.putBoolean(DARKMODE_KEY, value)

    fun saveInfo(login: String) {
        //settings.putString(TOKEN_KEY, "Bearer $token")
        settings.putString(USER_LOGIN_KEY, login)
    }

    fun getUserLogin(): String {
        return settings[USER_LOGIN_KEY, ""]
    }

    fun clear(): Boolean {
        settings.clear()
        return true
    }

    companion object {
        private const val USER_LOGIN_KEY = "user_login_key"
        private const val DARKMODE_KEY = "dark_mode_key"
    }
}
