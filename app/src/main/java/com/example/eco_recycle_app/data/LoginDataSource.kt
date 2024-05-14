package com.example.eco_recycle_app.data

import com.example.eco_recycle_app.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    val USER_PASSWORD: String = "123456"
    val USER_NAME: String = "vasquez"

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            if (USER_NAME == username && USER_PASSWORD == password)
                return Result.Success(LoggedInUser(password, username))
            else
                return Result.Error(Exception())
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}