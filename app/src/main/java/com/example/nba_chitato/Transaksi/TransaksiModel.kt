package com.example.nba_chitato.Transaksi

// Model data satu transaksi kebun
data class TransaksiModel(
    val judul: String,
    val keterangan: String,
    val nominal: Long,
    val jenis: String, // "masuk" atau "keluar"
    val fotoUrl: String
)
