package com.example.nba_chitato.Lainnya

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nba_chitato.MainReesultActivity
import com.example.nba_chitato.ProfilActivity
import com.example.nba_chitato.WebViewActivity
import com.example.nba_chitato.databinding.FragmentLainnyaBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LainnyaFragment : Fragment() {
    private var _binding: FragmentLainnyaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLainnyaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Lainnya"
        }

        binding.btnProfil.setOnClickListener {
            val intent = Intent(requireContext(), ProfilActivity::class.java)
            startActivity(intent)
        }

        binding.btnWebView.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(intent)
        }

        binding.btnSnack.setOnClickListener {
            val intent = Intent(requireContext(), MainReesultActivity::class.java)
            startActivity(intent)
        }

        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Keluar")
                .setMessage("Yakin ingin keluar dari PalmSys?")
                .setPositiveButton("Ya") { dialog, _ ->
                    // Hapus data login
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
