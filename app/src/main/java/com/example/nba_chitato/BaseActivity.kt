package com.example.nba_chitato

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.nba_chitato.Beranda.BerandaFragment
import com.example.nba_chitato.Harga.HargaFragment
import com.example.nba_chitato.Kantong.KantongFragment
import com.example.nba_chitato.Lainnya.LainnyaFragment
import com.example.nba_chitato.Transaksi.TransaksiFragment
import com.example.nba_chitato.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // BerandaFragment sebagai fragment default
        replaceFragment(BerandaFragment())

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beranda -> {
                    replaceFragment(BerandaFragment())
                    true
                }
                R.id.kantong -> {
                    replaceFragment(KantongFragment())
                    true
                }
                R.id.transaksi -> {
                    replaceFragment(TransaksiFragment())
                    true
                }
                R.id.harga -> {
                    replaceFragment(HargaFragment())
                    true
                }
                R.id.lainnya -> {
                    replaceFragment(LainnyaFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            //.addToBackStack(null) -> dinonaktifkan agar saat back langsung keluar aplikasi
            .commit()
    }
}
