package com.example.streamchattest.presentation.userlist.channelviewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.streamchattest.databinding.ItemChannelListBinding
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.extensions.currentUserUnreadCount
import io.getstream.chat.android.client.extensions.internal.lastMessage
import io.getstream.chat.android.core.internal.InternalStreamChatApi
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.ui.common.state.channels.actions.Cancel.channel
import io.getstream.chat.android.ui.feature.channels.list.ChannelListView
import io.getstream.chat.android.ui.feature.channels.list.adapter.ChannelListItem.ChannelItem
import io.getstream.chat.android.ui.feature.channels.list.adapter.ChannelListPayloadDiff
import io.getstream.chat.android.ui.feature.channels.list.adapter.viewholder.BaseChannelListItemViewHolder
import io.getstream.chat.android.ui.feature.channels.list.adapter.viewholder.ChannelListItemViewHolderFactory
import io.getstream.chat.android.ui.utils.extensions.getLastMessage
import java.text.SimpleDateFormat

class ChannelListItemViewHolderFactory : ChannelListItemViewHolderFactory() {

  var listener: ChannelListView.ChannelClickListener? = null

  override fun createChannelViewHolder(parentView: ViewGroup): ChannelViewHolder {
    return ChannelViewHolder(parentView, listener)
  }
}

class ChannelViewHolder(
  parent: ViewGroup,
  private val channelClickListener: ChannelListView.ChannelClickListener?,
  private val binding: ItemChannelListBinding = ItemChannelListBinding.inflate(
    LayoutInflater.from(parent.context),
    parent,
    false
  ),
) : BaseChannelListItemViewHolder(binding.root) {

  val receiver = ChatClient.instance().getCurrentUser()?.id

  override fun bind(channelItem: ChannelItem, diff: ChannelListPayloadDiff) {
    channelItem.run {
      val timeFormater = SimpleDateFormat.getTimeInstance()
      binding.apply {
        tvDate.text = channel.lastMessageAt?.let { timeFormater.format(it) }
        updateReadCount(channel)
        tvTitleRoom.text = channel.members.filterNot { it.user.id == receiver }.firstOrNull()?.user?.name
        tvMessage.text = channel.getLastMessage()?.text
        root.setOnClickListener { channelClickListener?.onClick(channelItem.channel) }
      }
    }
  }

  private fun updateReadCount(channel: Channel) {
    binding.tvUnread.text = if (channel.getLastMessage()?.user?.id != receiver)
      channel.currentUserUnreadCount.toString()
    else "0"
  }
}