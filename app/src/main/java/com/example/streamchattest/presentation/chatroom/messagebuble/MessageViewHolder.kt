package com.example.streamchattest.presentation.chatroom.messagebuble

import android.view.LayoutInflater
import android.view.ViewGroup
import io.getstream.chat.android.models.MessageType

//class MessageViewHolder : AmityMessagePagingAdapter.CustomViewHolderListener {
//  override fun getViewHolder(
//    inflater: LayoutInflater,
//    parent: ViewGroup,
//    viewType: Int
//  ): AmityChatMessageBaseViewHolder? {
//    return when (viewType) {
//      MessageType.MESSAGE_ID_TEXT_RECEIVER ->
//        constructSenderViewHolder(parent)
//
//      MessageType.MESSAGE_ID_TEXT_SENDER ->
//        constructReceiverViewHolder(parent)
//
//      else -> null //kalau audio atau image?
//    }
//  }
//
//  private fun constructSenderViewHolder(parent: ViewGroup) =
//    AmityTextMsgSenderViewHolder(
//      itemView = providePrimaryMessageView(parent).root,
//      itemViewModel = AmityTextMessageViewModel(),
//      context = parent.context
//    )
//
//  private fun constructReceiverViewHolder(parent: ViewGroup) =
//    AmityTextMsgReceiverViewHolder(
//      itemView = provideSecondaryMessageView(parent).root,
//      itemViewModel = AmityTextMessageViewModel(),
//      context = parent.context
//    )
//
//  private fun provideSecondaryMessageView(parent: ViewGroup) =
//    MessageBubleSecondaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//  private fun providePrimaryMessageView(parent: ViewGroup) =
//    MessageBublePrimaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//}