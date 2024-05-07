package com.example.myapplication

import android.content.Intent
import android.media.AsyncPlayer
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivitySongBinding
import java.util.Timer

class SongActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySongBinding.inflate(layoutInflater)
    }
    lateinit var song : Song
    lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        var title : String? = null
        var singer : String? = null

        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            title = intent.getStringExtra("title")
            singer = intent.getStringExtra("singer")
            binding.musicTitleTv.text = title
            binding.singerNameTv.text = singer
        }

        binding.lowerIb.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", title + " _ " + singer)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }
    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying",false)

            )
        }
        startTimer()
    }

    private fun setPlayer(song : Song){
        binding.musicTitleTv.text = intent.getStringExtra("title")!!
        binding.singerNameTv.text = intent.getStringExtra("singer")!!
        binding.playerMusicStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.playerMusicEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)


    }

    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying
        if(isPlaying){
            binding.songPauseIv.visibility = View.GONE
            binding.songMiniplayerIv.visibility = View.VISIBLE

        }

        else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("message", "뒤로가기 버튼 클릭")
        setResult(RESULT_OK, intent)
        finish()
    }
    private fun startTimer(){
        timer = Timer(song.playTime,song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills : Float = 0f

        override fun run(){
            super.run()
            try {
                while (true) {
                    if (second >= playTime) {
                        break
                    }

                    if (isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()
                        }
                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.playerMusicStartTimeTv.text =
                                    String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }

                    }
                }

                    }catch (e: InterruptedException){
                        Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
                    }


        }
    }
}