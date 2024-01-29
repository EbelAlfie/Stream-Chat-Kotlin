package com.example.streamchattest.presentation.login.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class LoginModel (
  val username: String,
  val pass: String
)

@Parcelize
data class UserModel (
  val priviledge: Priviledge,
  val username: String,
  val userId: String,
  val token: String,
): Parcelable

enum class Priviledge {
  Admin,
  Customer
}