package com.example.streamchattest.data

import com.example.streamchattest.ceritanyamapi.LoginDatabases
import com.example.streamchattest.presentation.login.model.LoginModel
import com.example.streamchattest.presentation.login.model.Priviledge
import com.example.streamchattest.presentation.login.model.Priviledge.Admin
import com.example.streamchattest.presentation.login.model.UserModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(): UserRepository {
  override fun login(auth: LoginModel, mode: Priviledge): UserModel? {
    return LoginDatabases.customers.find {
      passwordChecked(it.userId, auth.pass)
    }
  }

  private fun passwordChecked(database: String, input: String): Boolean {
    return database == input
  }
}