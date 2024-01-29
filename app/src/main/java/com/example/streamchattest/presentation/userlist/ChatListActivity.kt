package com.example.streamchattest.presentation.userlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.streamchattest.presentation.userlist.chatlistfragment.UserListFragment
import com.example.streamchattest.databinding.ActivityChatListBinding
import com.example.streamchattest.presentation.handler.StatusHandler
import com.example.streamchattest.presentation.userlist.chanellfragment.ChannelFragment
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListActivity: AppCompatActivity() {

  private val binding: ActivityChatListBinding by lazy {
    ActivityChatListBinding.inflate(layoutInflater)
  }

  private val viewModel: ChatListViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    loadData()
    registerObservers()
    setupTabView()
  }

  private fun registerObservers() {
    viewModel.loginState.observe(this) {
    }
  }

  private fun setupTabView() {
    binding.tlMode.apply {
      addOnTabSelectedListener(
        object: OnTabSelectedListener {
          override fun onTabSelected(tab: Tab) {
            val fragment = when (tab.position) {
              0 -> getChatHistoryFragment()
              else -> getQueryUserFragment()
            }

            attachFragment(fragment)
          }

          override fun onTabUnselected(tab: Tab?) {
          }

          override fun onTabReselected(tab: Tab?) {
          }

        }
      )

      attachFragment(getChatHistoryFragment())
    }

  }

  private fun getQueryUserFragment(): Fragment {
    return UserListFragment.newInstance(viewModel.userData)
  }

  private fun attachFragment(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(binding.containerChatList.id, fragment)
    transaction.commit()
  }

  private fun getChatHistoryFragment(): ChannelFragment {
    return ChannelFragment.newInstance(viewModel.userData)
  }

  private fun loadData() {
//    viewModel.userData = if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
//      intent.getParcelableExtra(EXTRA_USER, UserModel::class.java)
//    } else
//      intent.getParcelableExtra(EXTRA_USER)
  }

  companion object {
    const val EXTRA_USER = "EXTRA_USER"
    fun newIntent(from: Context): Intent {
      return Intent(from, ChatListActivity::class.java)
    }
  }
}