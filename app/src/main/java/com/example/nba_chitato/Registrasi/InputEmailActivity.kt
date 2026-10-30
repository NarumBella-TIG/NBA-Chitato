package com.example.nba_chitato.Registrasi

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nba_chitato.R
import com.example.nba_chitato.databinding.ActivityInputEmailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InputEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInputEmailBinding.inflate(layoutInflater)
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

        binding.btnLanjut.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim().lowercase()

            if (email.isEmpty()) {
                tampilkanError("Email Kosong", "Email tidak boleh kosong")
            } else if (!email.endsWith("@gmail.com") || email == "@gmail.com") {
                tampilkanError("Email Tidak Valid", "Email harus memakai alamat @gmail.com")
            } else {
                // Kirim email ke halaman Registrasi
                val intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra("email", email)
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
