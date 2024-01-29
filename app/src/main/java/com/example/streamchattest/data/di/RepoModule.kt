package com.example.streamchattest.data.di

import com.example.streamchattest.data.UserRepository
import com.example.streamchattest.data.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepoModule {

  @Binds
  fun provideUserRepo(repo: UserRepositoryImpl): UserRepository
}