package com.isabri.dragonballandroidavanzado.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.isabri.dragonballandroidavanzado.data.dataState.LoginState
import com.isabri.dragonballandroidavanzado.databinding.ActivityLoginBinding
import com.isabri.dragonballandroidavanzado.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeLoadingState()
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            bLogin.setOnClickListener {
                val user: String = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                viewModel.login(user, password)
            }
        }
    }

    private fun observeLoadingState() {
        viewModel.loginState.observe(this) {
            when(it) {
                is LoginState.Success -> {
                    binding.pbLogin.visibility = View.INVISIBLE
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                is LoginState.Failure -> {
                    Log.d("MyLog", it.errorMessage)
                    binding.pbLogin.visibility = View.INVISIBLE
                    Toast.makeText(this, "Error while login: wrong user or password", Toast.LENGTH_SHORT).show()
                }
                is LoginState.Loading -> {
                    binding.pbLogin.visibility = View.VISIBLE
                }
                is LoginState.InvalidPassword -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is LoginState.InvalidUser -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}