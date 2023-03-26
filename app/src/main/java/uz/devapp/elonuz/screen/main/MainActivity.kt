package uz.devapp.elonuz.screen.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import uz.devapp.elonuz.databinding.ActivityMainBinding
import uz.devapp.elonuz.screen.auth.LoginActivity
import uz.devapp.elonuz.screen.main.add_ad.AddAdsActivity
import uz.devapp.elonuz.utils.PrefUtils

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgAddAds.setOnClickListener {
            startActivity(Intent(this,
                if (PrefUtils.getToken()
                        .isEmpty()
                ) LoginActivity::class.java else AddAdsActivity::class.java
            )
            )
        }

        val navHostFragment = binding.fcvNavHost.getFragment<NavHostFragment>()
        binding.bnv.setupWithNavController(navHostFragment.navController)
    }
}