package com.example.streamchattest.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.streamchattest.data.UserRepository
import com.example.streamchattest.presentation.login.model.LoginModel
import com.example.streamchattest.presentation.login.model.Priviledge
import com.example.streamchattest.presentation.login.model.Priviledge.Customer
import com.example.streamchattest.presentation.login.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.ConnectionData
import io.getstream.chat.android.models.User
import io.getstream.result.Result
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val userRepository: UserRepository,
  private val client: ChatClient
): ViewModel() {

  val loginResult = MutableLiveData<Result<ConnectionData>>()
  fun validateLogin(auth: LoginModel, mode: Priviledge = Customer): UserModel? {
    return userRepository.login(auth, mode)
  }

  fun loginUser(userModel: UserModel) {
    client.connectUser(
      user = constructUser(userModel),
      token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiSzQ0NiJ9.6arUiFrXQyhZxJr1YuQ8lBG3A4Bz_jls3xC9su5gE1U",
    )
      .enqueue { result ->
        loginResult.postValue(result)
      }
  }

  fun constructUser(userModel: UserModel): User {
    return User (
      id = userModel.userId,
      name = userModel.username,
    )
  }
}