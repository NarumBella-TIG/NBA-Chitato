package com.example.nba_chitato.Registrasi

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nba_chitato.R
import com.example.nba_chitato.databinding.ActivityRegisterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar dengan tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_gelap)
        }

        // Email dari halaman sebelumnya, tidak bisa diubah di sini
        val email = intent.getStringExtra("email").toString()
        binding.inputEmail.setText(email)

        binding.btnDaftar.setOnClickListener {
            val nama = binding.inputNama.text.toString().trim()
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()

            if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                tampilkanError("Registrasi Gagal", "Semua isian wajib diisi")
            } else if (username.contains(" ")) {
                tampilkanError("Registrasi Gagal", "Username tidak boleh mengandung spasi")
            } else if (password.length < 6) {
                tampilkanError("Registrasi Gagal", "Password minimal 6 karakter")
            } else {
                // Simpan di SharedPreferences terpisah supaya tidak terhapus saat Keluar
                val dataRegistrasi = getSharedPreferences("data_registrasi", MODE_PRIVATE)
                val editor = dataRegistrasi.edit()
                editor.putString("nama", nama)
                editor.putString("email", email)
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

                val intent = Intent(this, RegisterSuccessActivity::class.java)
                intent.putExtra("nama", nama)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun tampilkanError(judul: String, pesan: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(judul)
            .setMessage(pesan)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
