package com.example.streamchattest.presentation.chatroom.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.streamchattest.databinding.ItemTemplateMessageBinding

class TemplateViewHolder(
  private val binding: ItemTemplateMessageBinding
): RecyclerView.ViewHolder(binding.root) {
  fun bindData(item: String, listener: ((String) -> Unit)?) {
    binding.tvTemplateChat.text = item
    binding.root.setOnClickListener {
      listener?.invoke(item)
    }
  }
}