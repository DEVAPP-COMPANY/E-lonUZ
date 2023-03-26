package uz.devapp.elonuz.screen.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.devapp.elonuz.R
import uz.devapp.elonuz.data.model.request.LoginRequest
import uz.devapp.elonuz.databinding.ActivityLoginBinding
import uz.devapp.elonuz.screen.main.MainActivity
import uz.devapp.elonuz.screen.main.ads.AdsViewModel
import uz.devapp.elonuz.utils.showMessage
import uz.devapp.elonuz.view.AdsAdapter

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        viewModel.errorLiveData.observe(this, Observer {
            showMessage(it)
        })

        viewModel.progressLiveData.observe(this) {
            binding.flProgress.visibility = if(it) View.VISIBLE else View.GONE
        }

        viewModel.authData.observe(this) {
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        binding.btnRegistration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }

        binding.btnSend.setOnClickListener {
            viewModel.login(LoginRequest(binding.edPhone.text.toString(), binding.edPassword.text.toString()))
        }
    }
}