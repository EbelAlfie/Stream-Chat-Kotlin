package com.example.streamchattest.presentation.userlist.chanellfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.streamchattest.databinding.FragmentChannelListBinding
import com.example.streamchattest.presentation.chatroom.ChatRoomActivity
import com.example.streamchattest.presentation.login.model.UserModel
import com.example.streamchattest.presentation.userlist.channelviewholder.ChannelListItemViewHolderFactory
import com.example.streamchattest.presentation.util.toUserModel
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.User
import io.getstream.chat.android.models.querysort.QuerySortByField
import io.getstream.chat.android.ui.feature.channels.list.ChannelListView
import io.getstream.chat.android.ui.feature.messages.MessageListActivity
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.channels.bindView

@AndroidEntryPoint
class ChannelFragment : Fragment() {

  private lateinit var binding: FragmentChannelListBinding

  private val viewModel: ChannelListViewModel by viewModels {
    createChannelListFactory()
  }

  private fun createChannelListFactory(): ViewModelProvider.Factory {
    return ChannelListViewModelFactory(
      filter = Filters.and(
        Filters.eq("type", "messaging"),
        Filters.`in`(
          "members",
          listOf(ChatClient.instance().getCurrentUser()!!.id)
        ),
      ),
      sort = QuerySortByField.descByName("last_updated"),
      limit = 30,
    )
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentChannelListBinding.inflate(layoutInflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupChannelListView()

  }

  private fun setupChannelListView() {
    ChannelListItemViewHolderFactory().also {
      it.listener = ChannelListView.ChannelClickListener {channel ->
        gotoChatRoomActivity(channel)
      }
      binding.channelListView.apply {
        setViewHolderFactory(it)
        viewModel.bindView(this, viewLifecycleOwner)
      }
    }
  }

  private fun gotoChatRoomActivity(it: Channel) {
    startActivity(ChatRoomActivity.createIntent(requireContext(), it.cid, it.type))
  }

  companion object {
    private const val EXTRA_USER = "EXTRA_USER"
    fun newInstance(user: User?): ChannelFragment {
      return ChannelFragment().also {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_USER, toUserModel(user))
        it.arguments = bundle
      }
    }
  }
}