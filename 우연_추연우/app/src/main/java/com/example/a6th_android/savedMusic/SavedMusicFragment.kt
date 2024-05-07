package com.example.a6th_android.savedMusic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a6th_android.databinding.FragmentStorageSavedalbumBinding

class SavedMusicFragment : Fragment() {

    lateinit var binding: FragmentStorageSavedalbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorageSavedalbumBinding.inflate(inflater, container, false)

        return binding.root
    }
}