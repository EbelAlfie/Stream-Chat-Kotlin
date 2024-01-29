package com.example.streamchattest.presentation.chatroom

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(): ViewModel() {
  var templates: ArrayList<String> = arrayListOf()
  var channelType: String = "messaging"
  var channelId: String = ""
}