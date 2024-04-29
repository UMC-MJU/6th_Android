package com.umc.floclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.floclone.databinding.FragmentSongBinding

class SongFragment : Fragment() {
    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        binding.songMixOffIv.setOnClickListener {
            setSongMixStatus(true)
        }

        binding.songMixOnIv.setOnClickListener {
            setSongMixStatus(false)
        }
        return binding.root
    }

    fun setSongMixStatus (isMix : Boolean) {
        if (isMix) {    // Toggle On일 경우
            binding.songMixOnIv.visibility = View.VISIBLE
            binding.songMixOffIv.visibility = View.GONE
        } else {
            binding.songMixOnIv.visibility = View.GONE
            binding.songMixOffIv.visibility = View.VISIBLE
        }
    }
}