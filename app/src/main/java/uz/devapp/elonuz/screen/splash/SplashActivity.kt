package uz.devapp.elonuz.screen.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.devapp.elonuz.databinding.ActivitySplashBinding
import uz.devapp.elonuz.screen.main.MainActivity

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, 2000)
    }
}