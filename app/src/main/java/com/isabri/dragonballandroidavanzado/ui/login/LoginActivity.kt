package com.isabri.dragonballandroidavanzado.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.isabri.dragonballandroidavanzado.R
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
        observeFetchingState()
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

    private fun observeFetchingState() {
        viewModel.stateLiveData.observe(this) {
            when(it) {
                is LoginViewModel.LoginState.Success -> {
                    binding.pbLogin.visibility = View.INVISIBLE
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                is LoginViewModel.LoginState.Failure -> {
                    Log.d("MyLog", it.errorMessage)
                    binding.pbLogin.visibility = View.INVISIBLE
                    Toast.makeText(this, "Error while login: ${it.errorMessage}", Toast.LENGTH_LONG).show()
                }
                is LoginViewModel.LoginState.Loading -> {
                    binding.pbLogin.visibility = View.VISIBLE
                }
            }
        }
    }
}