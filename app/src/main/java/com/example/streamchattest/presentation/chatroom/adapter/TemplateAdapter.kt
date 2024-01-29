package com.example.streamchattest.presentation.chatroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.streamchattest.databinding.ItemTemplateMessageBinding

class TemplateAdapter (): ListAdapter<String, TemplateViewHolder>(TemplateCallback) {

  var listener: ((String) -> Unit)? = null
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    TemplateViewHolder(ItemTemplateMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: TemplateViewHolder, position: Int) {
    val item = currentList[position]
    holder.bindData(item, listener)
  }

}