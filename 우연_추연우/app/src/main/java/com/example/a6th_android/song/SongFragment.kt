package com.example.a6th_android.song

import AlbumFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.a6th_android.MainActivity
import com.example.a6th_android.R
import com.example.a6th_android.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        binding.songMixoffTg.setOnClickListener {
            setToggleStatus(false)
        }
        binding.songMixonTg.setOnClickListener {
            setToggleStatus(true)
        }

        return binding.root
    }

    private fun setToggleStatus(isOn : Boolean) {
        if(isOn){
            binding.songMixoffTg.visibility = View.VISIBLE
            binding.songMixonTg.visibility = View.GONE
        } else {
            binding.songMixoffTg.visibility = View.GONE
            binding.songMixonTg.visibility = View.VISIBLE
        }
    }
}