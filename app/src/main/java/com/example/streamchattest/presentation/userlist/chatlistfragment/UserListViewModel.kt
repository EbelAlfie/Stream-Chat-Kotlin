package com.example.streamchattest.presentation.userlist.chatlistfragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.streamchattest.data.user.UserData
import com.example.streamchattest.presentation.login.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryUsersRequest
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.User
import io.getstream.result.Result
import io.getstream.result.call.enqueue
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
  private val client: ChatClient
) : ViewModel() {

  var userInfo: UserData? = null
  val userList = MutableLiveData<List<User>>()

  val channelInfo = MutableLiveData<Channel>()

  val request = QueryUsersRequest(
    filter = Filters.nin("id", client.getCurrentUser()?.id ?: ""),
    offset = 0,
    limit = 3,
  )

  fun queryUsers() {
    client.queryUsers(request).enqueue { result ->
      try {
        if (result.isSuccess) {
          userList.postValue(result.getOrThrow())
        } else {
          Log.d("QUERY ERROR", "queryUsers: ${result.errorOrNull()}")
        }
      } catch(e: Exception) {
        Log.d("QUERY ERROR", "queryUsers: ${e.message}")
      }
    }
  }

  fun createChatRoom(recipient: User) {
    Log.d("IDSssss", "${userInfo} ${recipient.id} ")
    userInfo?.let {
      ChatClient.instance().createChannel(
        channelType = "messaging",
        channelId = "",
        memberIds = listOf(recipient.id, it.password),
        extraData = mapOf()
      ).enqueue {
        when (it) {
          is Result.Success ->
            channelInfo.postValue(it.value)
          else -> {
            Log.d("CREATE CHANNEL", "${it.errorOrNull()}")
          }
        }
      }
    }
  }

}