package com.umc.floclone

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umc.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var title : String? = null
        var singer : String? = null

        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            title = intent.getStringExtra("title")
            singer = intent.getStringExtra("singer")
            binding.playerMusicTitleTv.text = title
            binding.playerMusicSingerTv.text = singer
        }

        binding.playerDownIb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", title + " _ " + singer)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.playerPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.playerPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
    }

    fun setPlayerStatus(isPlaying : Boolean) {
        if (isPlaying) {    // 재생중일 경우
            binding.playerPlayIv.visibility = View.GONE
            binding.playerPauseIv.visibility = View.VISIBLE
        } else {    // 일시정지한 경우
            binding.playerPlayIv.visibility = View.VISIBLE
            binding.playerPauseIv.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("message", "뒤로가기 버튼 클릭")
        setResult(RESULT_OK, intent)
        finish()
    }
}