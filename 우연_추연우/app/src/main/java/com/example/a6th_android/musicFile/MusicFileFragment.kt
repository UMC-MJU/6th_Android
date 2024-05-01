package com.example.a6th_android.musicFile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a6th_android.databinding.FragmentStorageMusicfileBinding

class MusicFileFragment : Fragment() {

    lateinit var binding: FragmentStorageMusicfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorageMusicfileBinding.inflate(inflater, container, false)

        return binding.root
    }
}