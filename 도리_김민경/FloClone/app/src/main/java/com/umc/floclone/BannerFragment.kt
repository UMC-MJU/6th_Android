package com.umc.floclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.floclone.databinding.FragmentBannerBinding

class BannerFragment(val imgRes : Int) : Fragment() {
    lateinit var binding : FragmentBannerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannerBinding.inflate(inflater, container, false)
        // 이미지의 개수가 변경됨
        binding.bannerImageIv.setImageResource(imgRes)
        return binding.root
    }
}