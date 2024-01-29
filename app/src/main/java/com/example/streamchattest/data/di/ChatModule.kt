package com.example.streamchattest.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.getstream.chat.android.client.ChatClient

@Module
@InstallIn(ViewModelComponent:: class)
class ChatModule {

  @Provides
  fun provideChatClient(): ChatClient {
    return ChatClient.instance()
  }
}