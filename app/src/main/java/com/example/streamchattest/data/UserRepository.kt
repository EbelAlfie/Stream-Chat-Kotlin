package com.example.streamchattest.data

import com.example.streamchattest.presentation.login.model.LoginModel
import com.example.streamchattest.presentation.login.model.Priviledge
import com.example.streamchattest.presentation.login.model.UserModel

interface UserRepository {
  fun login(auth: LoginModel, mode: Priviledge): UserModel?
}