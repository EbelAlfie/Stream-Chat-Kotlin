package com.example.streamchattest.presentation.userlist.chatlistfragment

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.streamchattest.data.user.UserData
import com.example.streamchattest.databinding.FragmentUserListBinding
import com.example.streamchattest.presentation.chatroom.ChatRoomActivity
import com.example.streamchattest.presentation.userlist.chatlistfragment.adapter.UserPagingAdapter
import com.example.streamchattest.presentation.userlist.chatlistfragment.adapter.UserPagingAdapter.OnUserClicked
import com.example.streamchattest.presentation.util.toUserModel
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.User

@AndroidEntryPoint
class UserListFragment : Fragment() {

  private lateinit var binding: FragmentUserListBinding

  private val viewModel: UserListViewModel by viewModels()

  private val pagingAdapter: UserPagingAdapter by lazy {
    UserPagingAdapter()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentUserListBinding.inflate(layoutInflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    loadArgs()
    setupRecyclerView()
    setObservers()
    viewModel.queryUsers()
  }

  private fun loadArgs() {
    viewModel.userInfo = if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
      arguments?.getParcelable(EXTRA_USER, UserData::class.java)
    } else
      arguments?.getParcelable(EXTRA_USER)
  }

  private fun setObservers() {
    viewModel.userList.observe(requireActivity()) {
      pagingAdapter.submitList(it)
    }

    viewModel.channelInfo.observe(requireActivity()) {
      gotoChatRoom(it)
    }
  }

  private fun setupRecyclerView() {
    pagingAdapter.listener = object : OnUserClicked {
      override fun onItemClicked(item: User) {
        startChatting(item)
      }
    }

    binding.rvUserlists.apply {
      layoutManager = LinearLayoutManager(requireContext())
      adapter = pagingAdapter
    }
  }

  private fun startChatting(item: User) {
    viewModel.createChatRoom(item)
  }

  private fun gotoChatRoom(channel: Channel) {
    startActivity(
      ChatRoomActivity.createIntent(requireContext(), channel.cid, channel.type)
    )
  }

  companion object {
    private const val EXTRA_USER = "EXTRA_USER"
    fun newInstance(user: User?): Fragment {
      return UserListFragment().also {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_USER, toUserModel(user))
        it.arguments = bundle
      }
    }
  }
}