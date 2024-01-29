package com.example.streamchattest.presentation.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.streamchattest.presentation.handler.StatusHandler
import com.example.streamchattest.presentation.login.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
  private val client: ChatClient
): ViewModel() {

  var userData: User? = client.getCurrentUser()

  val loginState= MutableLiveData<StatusHandler<Boolean>>()

}