package com.example.streamchattest.presentation.handler

sealed class StatusHandler <out V> {

  data class Success<V>(
    val data: V
  ): StatusHandler<V>()

  data class Error (
    val error: Throwable
  ): StatusHandler<Nothing>()
}