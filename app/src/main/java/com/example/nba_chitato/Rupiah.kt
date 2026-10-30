package com.example.nba_chitato

// Mengubah angka menjadi teks Rupiah, contoh: 8200000 -> "Rp8.200.000"
fun formatRupiah(nominal: Long): String {
    return "Rp" + String.format("%,d", nominal).replace(",", ".")
}
