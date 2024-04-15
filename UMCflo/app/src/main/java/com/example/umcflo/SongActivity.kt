package com.example.umcflo

import android.content.Intent
import android.media.AsyncPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.service.voice.VoiceInteractionSession.ActivityId
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umcflo.databinding.ActivitySongBinding
import com.example.umcflo.databinding.FragmentLookBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener {
            val song = Song(binding.songMusicTitleTv.text.toString(), binding.songSingerNameTv.text.toString())
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            setResult(RESULT_OK, intent)
            //데이터(intent) 를 인자로 가지는 함수. MainActivity로 데이터를 보내준다.
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }
    }


    fun setPlayerStatus(isPlaying: Boolean){
        if (isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }
}