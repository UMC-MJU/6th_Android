package com.example.a6th_android.song

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.a6th_android.R
import com.example.a6th_android.Song
import com.example.a6th_android.databinding.ActivitySongBinding


class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    lateinit var song : Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus()
        }

        binding.songRandomIv.setOnClickListener {
            setRandomStatus()
        }

    }

    private fun initSong() {
        if(intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getBooleanExtra("isRepeat", false),
                intent.getBooleanExtra("isRandom", false)
            )
        }
    }

    private fun setPlayer(song : Song) {
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second/60, song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime/60, song.playTime%60)
        binding.songProgressSb.progress = (song.second *1000/ song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    inner class Timer() {

    }

    private fun setPlayerStatus(isPlaying : Boolean) {
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        } else {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    private fun setRepeatStatus() {
        if(song.isRepeat){
            song.isRepeat = false
            binding.songRepeatIv.setColorFilter(R.drawable.btm_color_selector)
        } else {
            song.isRepeat = true
            binding.songRepeatIv.setColorFilter(R.drawable.btm_color_selector)
        }
    }

    private fun setRandomStatus() {
        if(song.isRandom){
            song.isRandom = false
            binding.songRandomIv.setColorFilter(Color.GRAY)
        } else {
            song.isRandom = true
            binding.songRandomIv.setColorFilter(R.color.purple_700)
        }
    }


}