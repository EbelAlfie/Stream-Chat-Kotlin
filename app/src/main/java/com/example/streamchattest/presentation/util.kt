package com.example.streamchattest.presentation

import com.example.streamchattest.data.user.UserData
import io.getstream.chat.android.models.User

object util {

  fun toUserModel(user: User?): UserData {
    return UserData(
      user?.name?: "",
      user?.id?: "",
    )
  }
}