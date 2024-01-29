package com.example.streamchattest.presentation.chatroom.adapter

import androidx.recyclerview.widget.DiffUtil

object TemplateCallback: DiffUtil.ItemCallback<String>() {
  override fun areItemsTheSame(oldItem: String, newItem: String)
    = oldItem == newItem

  override fun areContentsTheSame(oldItem: String, newItem: String)
    =  oldItem == newItem
}