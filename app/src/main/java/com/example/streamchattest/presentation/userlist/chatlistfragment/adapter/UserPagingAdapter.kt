package com.example.streamchattest.presentation.userlist.chatlistfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.streamchattest.databinding.ItemUserBinding
import io.getstream.chat.android.models.User

class UserPagingAdapter:
  ListAdapter<User, UserPagingViewHolder>(UserPagingDiffCallback) {

  var listener: OnUserClicked? = null

  interface OnUserClicked {
    fun onItemClicked(item: User)
  }

  override fun onBindViewHolder(holder: UserPagingViewHolder, position: Int) {
    val item = getItem(position) ?: return
    holder.bindData(item, listener)

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
  = UserPagingViewHolder(
    ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
  )
}