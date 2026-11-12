package com.example.nba_chitato.Transaksi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.nba_chitato.R
import com.example.nba_chitato.databinding.ItemTransaksiBinding
import com.example.nba_chitato.formatRupiah
import com.google.android.material.snackbar.Snackbar

class TransaksiAdapter(
    context: Context,
    private val transaksi: List<TransaksiModel>
) : ArrayAdapter<TransaksiModel>(context, 0, transaksi) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemTransaksiBinding = ItemTransaksiBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root

        val data = transaksi[position]

        // Foto pihak transaksi diambil dari URL dengan Glide
        Glide.with(context)
            .load(data.fotoUrl)
            .into(binding.imgFoto)

        binding.textJudul.text = data.judul
        binding.textKeterangan.text = data.keterangan

        // Pemasukan hijau dengan tanda +, pengeluaran merah dengan tanda -
        if (data.jenis == "masuk") {
            binding.textNominal.text = "+" + formatRupiah(data.nominal)
            binding.textNominal.setTextColor(context.getColor(R.color.hijau_daun))
        } else {
            binding.textNominal.text = "-" + formatRupiah(data.nominal)
            binding.textNominal.setTextColor(context.getColor(R.color.merah_tbs))
        }

        view.setOnClickListener {
            Snackbar.make(
                parent,
                data.judul + " (" + data.keterangan + ")",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return view
    }
}
