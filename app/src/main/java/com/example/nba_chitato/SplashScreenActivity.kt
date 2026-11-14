package com.example.nba_chitato

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.nba_chitato.OnBoarding.OnBoardingActivity
import com.example.nba_chitato.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        lifecycleScope.launch {
            delay(2000) //simulasi pengambilan data selama 2 detik

            // Cek apakah OnBoarding sudah pernah dilihat, lalu cek status login
            val appPref = getSharedPreferences("app_pref", MODE_PRIVATE)
            val onBoardingSelesai = appPref.getBoolean("onboarding_selesai", false)
            val isLogin = sharedPref.getBoolean("isLogin", false)
            if (!onBoardingSelesai) {
                val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
                startActivity(intent)
            } else if (isLogin) {
                val intent = Intent(this@SplashScreenActivity, BaseActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashScreenActivity, AuthActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }
}
