package com.example.nba_chitato.Registrasi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nba_chitato.databinding.ActivityRegisterSuccessBinding

class RegisterSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Sapa pengguna dengan nama yang baru didaftarkan
        val nama = intent.getStringExtra("nama")
        binding.textInfo.text = "Halo $nama, akun kamu sudah tersimpan. Silakan masuk."

        // Halaman Registrasi sudah ditutup, jadi finish() langsung kembali ke halaman Masuk
        binding.btnKeLogin.setOnClickListener {
            finish()
        }
    }
}
