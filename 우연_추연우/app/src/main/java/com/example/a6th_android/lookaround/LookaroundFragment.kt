package com.example.a6th_android.lookaround

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a6th_android.databinding.FragmentLookaroundBinding

class LookaroundFragment : Fragment() {

    private lateinit var binding: FragmentLookaroundBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLookaroundBinding.inflate(inflater, container, false)


        return binding.root
    }

}