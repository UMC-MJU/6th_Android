package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentSongBinding


class SongFragment : Fragment() {

    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        return binding.root
    }
}

class SongFragment_2 : AppCompatActivity() {

    lateinit var binding2 : FragmentSongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = FragmentSongBinding.inflate(layoutInflater)
        setContentView(binding2.root)
        binding2.albumFirstOnIv.setOnClickListener {
            setMixStatus(false)
        }
        binding2.albumFirstOffIv.setOnClickListener {
            setMixStatus(true)
        }
    }

    fun setMixStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding2.albumFirstOnIv.visibility = View.VISIBLE
            binding2.albumFirstOffIv.visibility = View.GONE
        } else {
            binding2.albumFirstOnIv.visibility = View.GONE
            binding2.albumFirstOffIv.visibility = View.VISIBLE

        }

    }
}