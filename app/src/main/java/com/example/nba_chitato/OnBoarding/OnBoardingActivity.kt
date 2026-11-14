package com.example.nba_chitato.OnBoarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nba_chitato.AuthActivity
import com.example.nba_chitato.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tiga halaman pengenalan PalmSys di dalam ViewPager2
        val fragmentsList = listOf(OnBoarding1Fragment(), OnBoarding2Fragment(), OnBoarding3Fragment())
        val adapter = OnBoardingAdapter(this, fragmentsList)
        binding.onBoardingViewPager.adapter = adapter
        binding.dotIndicator.attachTo(binding.onBoardingViewPager)

        binding.btnLewati.setOnClickListener {
            selesai()
        }
    }

    // Dipanggil dari tombol Lewati dan tombol Mulai di halaman terakhir
    fun selesai() {
        // Disimpan di app_pref (bukan user_pref) supaya tidak terhapus saat logout
        val sharedPref = getSharedPreferences("app_pref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("onboarding_selesai", true)
        editor.apply()

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}
