package com.example.streamchattest.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import com.example.streamchattest.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.logger.ChatLoggerHandler
import io.getstream.chat.android.client.notifications.handler.NotificationHandlerFactory
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory

@HiltAndroidApp
class StreamChapp: Application() {
  override fun onCreate() {
    super.onCreate()
    //val offlinePlugin = StreamOfflinePluginFactory(applicationContext)

    val statePluginFactory = StreamStatePluginFactory(
      config = StatePluginConfig(
        backgroundSyncEnabled = true,
        userPresence = true,
      ),
      appContext = applicationContext,
    )
    ChatClient.Builder(BuildConfig.STREAM_KEY, applicationContext)
      .withPlugins(statePluginFactory)
      .logLevel(ChatLogLevel.ALL)
      .loggerHandler(object : ChatLoggerHandler {
        override fun logT(throwable: Throwable) {
          Log.d("ERROR1", throwable.message.toString())
        }

        override fun logT(tag: Any, throwable: Throwable) {
          Log.d("2 $tag", throwable.message.toString())
        }

        override fun logI(tag: Any, message: String) {
          Log.d("3 $tag", message)
        }

        override fun logD(tag: Any, message: String) {
          Log.d("4 $tag", message)
        }

        override fun logW(tag: Any, message: String) {
          Log.d("5 $tag", message)
        }

        override fun logE(tag: Any, message: String) {
          Log.d("6 $tag", message)
        }

        override fun logE(tag: Any, message: String, throwable: Throwable) {
          Log.d("7 $tag", "$message ${throwable.message}")
        }
      })
      .build()
  }

  private fun configureNotif() {
    val notificationHandler = NotificationHandlerFactory.createNotificationHandler(
      context = applicationContext,
      newMessageIntent = {
          message: Message,
          channel: Channel,
        ->
        // Return the intent you want to be triggered when the notification is clicked
        val intent: Intent = Intent()
        intent
      }
    )
  }
}