package com.example.nba_chitato.Harga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nba_chitato.databinding.FragmentHargaBinding

class HargaFragment : Fragment() {
    private var _binding: FragmentHargaBinding? = null
    private val binding get() = _binding!!

    // Data harga pupuk: title = nama pupuk, desc = harga per karung
    private val hargaPupuk = listOf(
        mapOf("title" to "NPK", "desc" to "Rp380.000 per karung 50 kg"),
        mapOf("title" to "Urea", "desc" to "Rp290.000 per karung 50 kg"),
        mapOf("title" to "KCl", "desc" to "Rp350.000 per karung 50 kg"),
        mapOf("title" to "Dolomit", "desc" to "Rp75.000 per karung 50 kg"),
        mapOf("title" to "TSP", "desc" to "Rp320.000 per karung 50 kg"),
        mapOf("title" to "Borate", "desc" to "Rp45.000 per kg")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHargaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Harga TBS dan Pupuk"
        }

        // ListView dengan SimpleAdapter dan layout bawaan simple_list_item_2
        val adapter = SimpleAdapter(
            requireContext(),
            hargaPupuk,
            android.R.layout.simple_list_item_2,
            arrayOf("title", "desc"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        binding.listHargaPupuk.adapter = adapter

        binding.listHargaPupuk.setOnItemClickListener { _, _, position, _ ->
            val pupuk = hargaPupuk[position]
            Toast.makeText(requireContext(), "${pupuk["title"]}: ${pupuk["desc"]}", Toast.LENGTH_SHORT).show()
        }
    }
}
