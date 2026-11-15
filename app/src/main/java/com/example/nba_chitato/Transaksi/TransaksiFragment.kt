package com.example.nba_chitato.Transaksi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nba_chitato.databinding.FragmentTransaksiBinding

class TransaksiFragment : Fragment() {
    private var _binding: FragmentTransaksiBinding? = null
    private val binding get() = _binding!!

    // Data contoh riwayat transaksi kebun (foto pihak transaksi dari URL)
    private val transaksiList = listOf(
        TransaksiModel("Jual TBS 1,2 ton", "14 Okt · PT Sawit Jaya", 3780000, "masuk", "https://api.dicebear.com/9.x/notionists/png?backgroundColor=f1e9d8&seed=PTSawitJaya"),
        TransaksiModel("Upah pemanen", "14 Okt · Pak Udin", 900000, "keluar", "https://api.dicebear.com/9.x/notionists/png?backgroundColor=f1e9d8&seed=PakUdin"),
        TransaksiModel("Pupuk NPK 2 karung", "10 Okt · Toko Tani Makmur", 760000, "keluar", "https://api.dicebear.com/9.x/notionists/png?backgroundColor=f1e9d8&seed=TokoTaniMakmur"),
        TransaksiModel("Jual TBS 0,9 ton", "2 Okt · RAM Pak Hasan", 2835000, "masuk", "https://api.dicebear.com/9.x/notionists/png?backgroundColor=f1e9d8&seed=PakHasan"),
        TransaksiModel("Upah pemupukan", "1 Okt · Bu Rina", 400000, "keluar", "https://api.dicebear.com/9.x/notionists/png?backgroundColor=f1e9d8&seed=BuRina"),
        TransaksiModel("Servis egrek dan dodos", "28 Sep · Bengkel Jaya", 150000, "keluar", "https://api.dicebear.com/9.x/notionists/png?backgroundColor=f1e9d8&seed=BengkelJaya"),
        TransaksiModel("Jual TBS 1,1 ton", "18 Sep · PT Sawit Jaya", 3410000, "masuk", "https://api.dicebear.com/9.x/notionists/png?backgroundColor=f1e9d8&seed=PTSawitJaya")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransaksiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Transaksi"
        }

        // Tombol kategori di GridLayout
        binding.btnJualTbs.setOnClickListener { pesanKategori("Jual TBS") }
        binding.btnPupuk.setOnClickListener { pesanKategori("Pupuk") }
        binding.btnUpah.setOnClickListener { pesanKategori("Upah Panen") }
        binding.btnAlat.setOnClickListener { pesanKategori("Alat Kebun") }

        // Riwayat transaksi memakai ListView dengan custom adapter
        binding.listTransaksi.adapter = TransaksiAdapter(requireContext(), transaksiList)

        // Filter riwayat dengan ChipGroup
        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            val dipilih = checkedIds.firstOrNull()
            val hasil = when (dipilih) {
                binding.chipMasuk.id -> transaksiList.filter { it.jenis == "masuk" }
                binding.chipKeluar.id -> transaksiList.filter { it.jenis == "keluar" }
                else -> transaksiList
            }
            binding.listTransaksi.adapter = TransaksiAdapter(requireContext(), hasil)
        }
    }

    private fun pesanKategori(kategori: String) {
        Toast.makeText(requireContext(), "Catat transaksi $kategori (segera hadir)", Toast.LENGTH_SHORT).show()
    }
}
