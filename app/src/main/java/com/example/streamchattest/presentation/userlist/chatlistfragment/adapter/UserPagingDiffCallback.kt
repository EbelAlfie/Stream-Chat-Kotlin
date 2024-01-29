package com.example.streamchattest.presentation.userlist.chatlistfragment.adapter

import androidx.recyclerview.widget.DiffUtil
import io.getstream.chat.android.models.User

object UserPagingDiffCallback: DiffUtil.ItemCallback<User>() {
  override fun areItemsTheSame(oldItem: User, newItem: User)
  = oldItem == newItem

  override fun areContentsTheSame(oldItem: User, newItem: User)
  =  oldItem == newItem
}