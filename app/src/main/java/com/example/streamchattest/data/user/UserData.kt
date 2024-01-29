package com.example.streamchattest.data.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData (
  val userName: String,
  val password: String
): Parcelable