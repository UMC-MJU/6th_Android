package com.example.a6th_android.pannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a6th_android.databinding.FragmentPannelBinding

class PannelFragment(val imgRes : Int) : Fragment() {

    lateinit var  binding : FragmentPannelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPannelBinding.inflate(inflater,container,false)

        binding.bannerImgIv.setImageResource((imgRes))

        return binding.root
    }
}