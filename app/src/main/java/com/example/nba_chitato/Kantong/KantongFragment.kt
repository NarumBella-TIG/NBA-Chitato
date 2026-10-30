package com.example.nba_chitato.Kantong

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nba_chitato.databinding.BottomSheetTransferBinding
import com.example.nba_chitato.databinding.FragmentKantongBinding
import com.example.nba_chitato.formatRupiah
import com.google.android.material.bottomsheet.BottomSheetDialog

class KantongFragment : Fragment() {
    private var _binding: FragmentKantongBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataKantong: SharedPreferences

    // Urutan kantong: 0 = Dana Kebun, 1 = Dana Pupuk, 2 = Dana Replanting
    private val namaKantong = arrayOf("Dana Kebun", "Dana Pupuk", "Dana Replanting")
    private val kunciKantong = arrayOf("kebun", "pupuk", "replanting")
    private val saldoAwal = longArrayOf(8200000, 2750000, 1500000)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKantongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Kantong"
        }

        // Saldo kantong disimpan di SharedPreferences supaya hasil transfer tidak hilang
        dataKantong = requireContext().getSharedPreferences("data_kantong", MODE_PRIVATE)
        tampilkanSaldo()

        // Ketuk kartu kantong untuk membuka Bottom Sheet transfer
        binding.cardDanaKebun.setOnClickListener { bukaTransfer(0) }
        binding.cardDanaPupuk.setOnClickListener { bukaTransfer(1) }
        binding.cardDanaReplanting.setOnClickListener { bukaTransfer(2) }
    }

    private fun ambilSaldo(i: Int): Long {
        return dataKantong.getLong(kunciKantong[i], saldoAwal[i])
    }

    private fun tampilkanSaldo() {
        binding.textDanaKebunSaldo.text = formatRupiah(ambilSaldo(0))
        binding.textDanaPupukSaldo.text = formatRupiah(ambilSaldo(1))
        binding.textDanaReplantingSaldo.text = formatRupiah(ambilSaldo(2))
        binding.textTotal.text = formatRupiah(ambilSaldo(0) + ambilSaldo(1) + ambilSaldo(2))
    }

    private fun bukaTransfer(asal: Int) {
        // Pola dari tutorial: buat BottomSheetDialog, isi dengan layout, lalu show()
        val dialog = BottomSheetDialog(requireContext())
        val sheet = BottomSheetTransferBinding.inflate(layoutInflater)
        dialog.setContentView(sheet.root)

        // Dua kantong lain menjadi pilihan tujuan
        val tujuan = (0..2).filter { it != asal }
        sheet.textAsal.text = "Dari " + namaKantong[asal] + " · saldo " + formatRupiah(ambilSaldo(asal))
        sheet.radioTujuan1.text = namaKantong[tujuan[0]]
        sheet.radioTujuan2.text = namaKantong[tujuan[1]]

        sheet.btnTransfer.setOnClickListener {
            val teksNominal = sheet.inputNominal.text.toString()
            val ke = when {
                sheet.radioTujuan1.isChecked -> tujuan[0]
                sheet.radioTujuan2.isChecked -> tujuan[1]
                else -> -1
            }

            if (ke == -1) {
                Toast.makeText(requireContext(), "Pilih kantong tujuan dulu", Toast.LENGTH_SHORT).show()
            } else if (teksNominal.isEmpty() || teksNominal.toLong() <= 0) {
                Toast.makeText(requireContext(), "Nominal belum diisi", Toast.LENGTH_SHORT).show()
            } else if (teksNominal.toLong() > ambilSaldo(asal)) {
                Toast.makeText(requireContext(), "Saldo " + namaKantong[asal] + " tidak cukup", Toast.LENGTH_SHORT).show()
            } else {
                val nominal = teksNominal.toLong()
                val editor = dataKantong.edit()
                editor.putLong(kunciKantong[asal], ambilSaldo(asal) - nominal)
                editor.putLong(kunciKantong[ke], ambilSaldo(ke) + nominal)
                editor.apply()

                tampilkanSaldo()
                Toast.makeText(
                    requireContext(),
                    formatRupiah(nominal) + " dipindah ke " + namaKantong[ke],
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}
