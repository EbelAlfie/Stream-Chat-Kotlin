package com.example.streamchattest.presentation.userlist.chatlistfragment.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.streamchattest.databinding.ItemUserBinding
import com.example.streamchattest.presentation.userlist.chatlistfragment.adapter.UserPagingAdapter.OnUserClicked
import io.getstream.chat.android.models.User

class UserPagingViewHolder(
  private val binding: ItemUserBinding
): RecyclerView.ViewHolder(binding.root) {

  fun bindData(item: User, listener: OnUserClicked?) {
    binding.apply {
      tvUsername.text = item.name
      root.setOnClickListener {
        listener?.onItemClicked(item)
      }
    }
  }

}