package com.example.nba_chitato.Beranda

import android.content.ActivityNotFoundException
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nba_chitato.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.nba_chitato.databinding.FragmentBerandaBinding
import com.example.nba_chitato.formatRupiah

class BerandaFragment : Fragment() {
    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    // Data tips perawatan kebun untuk RecyclerView
    private val tipsList = listOf(
        TipsModel("Pupuk tepat waktu", "Pemupukan dua kali setahun, di awal dan akhir musim hujan.", R.drawable.ic_daun),
        TipsModel("Rotasi panen 7-10 hari", "Panen rutin supaya buah tidak lewat matang dan brondolan tidak terbuang.", R.drawable.ic_kalender),
        TipsModel("Sisihkan dana replanting", "Kebun di atas 25 tahun perlu diremajakan. Mulai menabung dari sekarang.", R.drawable.ic_kantong),
        TipsModel("Catat setiap transaksi", "Catatan rapi memudahkan menghitung untung tiap kali panen.", R.drawable.ic_transaksi)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Beranda"
        }

        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)

        // Sapaan memakai username yang disimpan saat login
        val username = sharedPref.getString("username", "")
        binding.textHalo.text = "Halo, $username"

        // Saldo tiap kantong (sama dengan yang dipakai menu Kantong)
        val dataKantong = requireContext().getSharedPreferences("data_kantong", MODE_PRIVATE)
        val kebun = dataKantong.getLong("kebun", 8200000)
        val pupuk = dataKantong.getLong("pupuk", 2750000)
        val replanting = dataKantong.getLong("replanting", 1500000)
        binding.textSaldo.text = formatRupiah(kebun + pupuk + replanting)

        // Grid menu cepat: pindah tab Bottom Navigation
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        binding.btnMenuKantong.setOnClickListener {
            bottomNav.selectedItemId = R.id.kantong
        }
        binding.btnMenuTransaksi.setOnClickListener {
            bottomNav.selectedItemId = R.id.transaksi
        }
        binding.btnMenuHarga.setOnClickListener {
            bottomNav.selectedItemId = R.id.harga
        }

        // RecyclerView tips kebun dengan GridLayoutManager 2 kolom
        val adapter = TipsAdapter(tipsList) { selectedItem ->
            Toast.makeText(requireContext(), selectedItem.judul + ": " + selectedItem.isi, Toast.LENGTH_LONG).show()
        }
        binding.rvTips.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
        }

        binding.btnBagikan.setOnClickListener {
            val laporan = "Laporan Keuangan Kebun PalmSys\n" +
                    "Pemilik: $username\n\n" +
                    "Dana Kebun: " + formatRupiah(kebun) + "\n" +
                    "Dana Pupuk: " + formatRupiah(pupuk) + "\n" +
                    "Dana Replanting: " + formatRupiah(replanting) + "\n" +
                    "Total saldo: " + formatRupiah(kebun + pupuk + replanting)

            // Pola dari tutorial: Intent ACTION_SEND berisi teks biasa
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, laporan)
            intent.setPackage("com.whatsapp")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // WhatsApp tidak terpasang: tampilkan pilihan aplikasi lain
                intent.setPackage(null)
                startActivity(Intent.createChooser(intent, "Bagikan laporan lewat"))
            }
        }
    }
}
