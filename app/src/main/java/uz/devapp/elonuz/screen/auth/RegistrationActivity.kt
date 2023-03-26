package uz.devapp.elonuz.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.devapp.elonuz.data.model.request.RegistrationRequest
import uz.devapp.elonuz.databinding.ActivityRegistrationBinding
import uz.devapp.elonuz.screen.main.MainActivity
import uz.devapp.elonuz.utils.showMessage

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        viewModel.errorLiveData.observe(this, Observer {
            showMessage(it)
        })

        viewModel.progressLiveData.observe(this) {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.authData.observe(this) {
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnSend.setOnClickListener {
            viewModel.registration(
                RegistrationRequest(
                    binding.edPhone.text.toString(),
                    binding.edFullname.text.toString(),
                    binding.edPassword.text.toString()
                )
            )
        }
    }
}