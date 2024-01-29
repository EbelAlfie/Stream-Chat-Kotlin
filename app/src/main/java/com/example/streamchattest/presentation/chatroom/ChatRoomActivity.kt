package com.example.streamchattest.presentation.chatroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.streamchattest.ceritanyamapi.TempletMesseg
import com.example.streamchattest.databinding.ActivityChatRoomBinding
import com.example.streamchattest.presentation.chatroom.adapter.TemplateAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.ui.common.state.messages.Edit
import io.getstream.chat.android.ui.common.state.messages.MessageMode
import io.getstream.chat.android.ui.common.state.messages.Reply
import io.getstream.chat.android.ui.common.state.messages.list.DeleteMessage
import io.getstream.chat.android.ui.common.state.messages.list.EditMessage
import io.getstream.chat.android.ui.common.state.messages.list.SendAnyway
import io.getstream.chat.android.ui.feature.messages.MessageListActivity
import io.getstream.chat.android.ui.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListHeaderViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.messages.bindView

@AndroidEntryPoint
class ChatRoomActivity : AppCompatActivity() {
  private val binding: ActivityChatRoomBinding by lazy {
    ActivityChatRoomBinding.inflate(layoutInflater)
  }

  private val activityViewModel: ChatRoomViewModel by viewModels()

  private fun factory() = MessageListViewModelFactory(
    this,
    cid = "${activityViewModel.channelType}:${activityViewModel.channelId}"
  )

  private val toolbarViewModel: MessageListHeaderViewModel by viewModels {
    factory()
  }

  private val messageComposerViewModel: MessageComposerViewModel by viewModels {
    factory()
  }

  private val messageListViewModel: MessageListViewModel by viewModels {
    factory()
  }

  private val templateAdapter: TemplateAdapter by lazy { TemplateAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    loadChannelData()
    setupViews()
  }

  private fun setupViews() {
    binding.apply {
      setupToolbarView()
      setupMessageListView()
      setupTemplateChat()
      setupComposerView()
    }
  }

  private fun setupToolbarView() {
    binding.messageListHeaderView.apply {
      toolbarViewModel.bindView(this, this@ChatRoomActivity)
      setBackButtonClickListener {
        messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
      }
    }
  }

  private fun setupMessageListView() {
    binding.messageListView.run {
      setEditMessageEnabled(false)
      setDeleteMessageEnabled(false)
      setMessageFlagEnabled(false)

      messageListViewModel.bindView(this, this@ChatRoomActivity)

      messageListViewModel.state.observe(this@ChatRoomActivity) {
        when (it) {
          is MessageListViewModel.State.Loading -> Unit
          is MessageListViewModel.State.Result -> Unit
          is MessageListViewModel.State.NavigateUp -> {
            finish()
          }
        }
      }

      setModeratedMessageHandler { message, action ->
        when (action) {
          DeleteMessage -> {/*disable*/}
          EditMessage -> {/*disable*/}
          SendAnyway -> messageListViewModel.onEvent(MessageListViewModel.Event.RetryMessage(message))
          else -> Unit
        }
      }
    }
  }

  private fun setupComposerView() {
    binding.messageComposerView.apply {
      messageComposerViewModel.bindView(this, this@ChatRoomActivity)

      messageListViewModel.mode.observe(this@ChatRoomActivity) { mode ->
        when (mode) {
          is MessageMode.MessageThread -> {
            toolbarViewModel.setActiveThread(mode.parentMessage)
            messageComposerViewModel.setMessageMode(MessageMode.MessageThread(mode.parentMessage))
          }
          is MessageMode.Normal -> {
            toolbarViewModel.resetThread()
            messageComposerViewModel.leaveThread()
          }
        }
      }
      binding.messageListView.setMessageReplyHandler { _, message ->
        messageComposerViewModel.performMessageAction(Reply(message))
      }
      binding.messageListView.setMessageEditHandler { message ->
        messageComposerViewModel.performMessageAction(Edit(message))
      }
      binding.messageListView.setModeratedMessageHandler { message, action ->
        when (action) {
          DeleteMessage -> messageListViewModel.onEvent(MessageListViewModel.Event.DeleteMessage(message))
          EditMessage -> messageComposerViewModel.performMessageAction(Edit(message))
          SendAnyway -> messageListViewModel.onEvent(MessageListViewModel.Event.RetryMessage(message))
          else -> Unit
        }
      }
      binding.messageListView.setAttachmentReplyOptionClickHandler { result ->
        messageListViewModel.getMessageById(result.messageId)?.let { message ->
          messageComposerViewModel.performMessageAction(Reply(message))
        }
      }
    }
  }

  private fun setupTemplateChat() {
    binding.rvChatTemplate.apply {
      layoutManager =
        LinearLayoutManager(this@ChatRoomActivity, LinearLayoutManager.HORIZONTAL, false)
      adapter = templateAdapter.also {
        it.listener = ::onTemplateSelected
        it.submitList(activityViewModel.templates)
      }
      MessageListActivity
    }
  }

  private fun loadChannelData() {
    activityViewModel.channelId = intent.getStringExtra(INTENT_CHANNEL_ID) ?: ""
    activityViewModel.channelType = intent.getStringExtra(INTENT_CHANNEL_TYPE) ?: ""
    activityViewModel.templates = intent.getStringArrayListExtra(EXTRA_TEMPE) ?: arrayListOf()
  }

  private fun onTemplateSelected(chat: String) {
    messageComposerViewModel.setMessageInput(chat)
  }

  companion object {
    private const val INTENT_CHANNEL_ID = "channelID"
    private const val INTENT_CHANNEL_TYPE = "channelType"
    private const val EXTRA_TEMPE = "template"

    fun createIntent(
      context: Context,
      channelId: String,
      channelType: String = "messaging"
    ): Intent {
      return Intent(context, ChatRoomActivity::class.java).apply {
        putExtra(INTENT_CHANNEL_ID, channelId)
        putExtra(INTENT_CHANNEL_TYPE, channelType)
        putStringArrayListExtra(EXTRA_TEMPE, TempletMesseg.messages)
      }
    }
  }
}