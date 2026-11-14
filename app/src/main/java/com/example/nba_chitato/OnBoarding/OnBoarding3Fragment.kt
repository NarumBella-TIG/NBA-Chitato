package com.example.nba_chitato.OnBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nba_chitato.databinding.FragmentOnBoarding3Binding

class OnBoarding3Fragment : Fragment() {
    private var _binding: FragmentOnBoarding3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Halaman terakhir: tombol Mulai menutup OnBoarding lalu ke halaman login
        binding.btnMulai.setOnClickListener {
            (requireActivity() as OnBoardingActivity).selesai()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
