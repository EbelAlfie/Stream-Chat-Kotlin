package com.example.streamchattest.presentation.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.streamchattest.databinding.ActivityLoginBinding
import com.example.streamchattest.presentation.login.model.LoginModel
import com.example.streamchattest.presentation.userlist.ChatListActivity
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import io.getstream.result.Result

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

  private val viewModel: LoginViewModel by viewModels()

  private val binding: ActivityLoginBinding by lazy {
    ActivityLoginBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupViews()
    isUserAlreadyConnected()
    setupObservers()
  }

  private fun setupObservers() {
    viewModel.loginResult.observe(this) {
      when (it) {
        is Result.Success -> gotoChatListActivity(it.value.user)
        is Result.Failure -> {
          Log.d("GAGAL LOGIN", it.value.message)
        }
      }
    }
  }

  private fun isUserAlreadyConnected() {
    val currentUser = ChatClient.instance().getCurrentUser()
    if (currentUser != null)
      gotoChatListActivity(currentUser)
  }

  private fun setupViews() {
    binding.apply {
      btnSubmit.setOnClickListener {
        validateInput()
      }
    }
  }

  private fun validateInput() {
    binding.run {
      val name = tieUsername.text.toString()
      val pass = tiePassword.text.toString()
      if (name.isBlank()) return
      if (pass.isBlank()) return
      viewModel.validateLogin(LoginModel(name, pass)).let {
        if (it != null) viewModel.loginUser(it)
      }
    }
  }

  private fun gotoChatListActivity(data: User) {
    startActivity(ChatListActivity.newIntent(this))
  }
}