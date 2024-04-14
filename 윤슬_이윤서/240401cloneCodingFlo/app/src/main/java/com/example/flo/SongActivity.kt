package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songBtnDownIv.setOnClickListener{
            finish()
        }
        binding.songBtnStartIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songBtnPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songTxtTitleTv.text = intent.getStringExtra("title")
            binding.songTxtSingerTv.text = intent.getStringExtra("singer")
        }
    }

    fun setPlayerStatus(isPlaying : Boolean) {
        if (isPlaying) {
            binding.songBtnStartIv.visibility = View.VISIBLE
            binding.songBtnPauseIv.visibility = View.GONE
        }
        else {
            binding.songBtnStartIv.visibility = View.GONE
            binding.songBtnPauseIv.visibility = View.VISIBLE
        }
    }
}