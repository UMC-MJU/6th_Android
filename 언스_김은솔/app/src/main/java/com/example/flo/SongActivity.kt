package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flo.databinding.ActivitySongBinding
import java.util.Timer
import com.google.gson.Gson

class SongActivity : AppCompatActivity () {

    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        var title : String? = null
        var singer : String? = null

        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            title = intent.getStringExtra("title")
            singer = intent.getStringExtra("singer")
            binding.songTitle.text = title
            binding.songSinger.text = singer
        }

        binding.songDownIb.setOnClickListener {
            finish()
        }
        binding.songPlayBtn.setOnClickListener {
            setPlayStatus(false)
        }
        binding.songPauseBtn.setOnClickListener {
            setPlayStatus(true)
        }
        binding.songRepeatBtn.setOnClickListener {
            setRepeatStatus(false)
        }
        binding.songRepeatOnBtn.setOnClickListener {
            setRepeatStatus(true)
        }
        binding.songRandomBtn.setOnClickListener {
            setRandomStatus(false)
        }
        binding.songRandomOnBtn.setOnClickListener {
            setRandomStatus(true)
        }
    }


    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songTitle.text = intent.getStringExtra("title")!!
        binding.songSinger.text = intent.getStringExtra("singer")!!
        binding.songStartTime.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTime.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songPlayBar.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        setPlayerStatus(song.isPlaying)
    }


    fun setPlayStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songPlayBtn.visibility = View.VISIBLE
            binding.songPauseBtn.visibility = View.GONE
        }
        else {
            binding.songPlayBtn.visibility = View.GONE
            binding.songPauseBtn.visibility = View.VISIBLE
        }
    }

    fun setRepeatStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songRepeatBtn.visibility = View.VISIBLE
            binding.songRepeatOnBtn.visibility = View.GONE
        }
        else {
            binding.songRepeatBtn.visibility = View.GONE
            binding.songRepeatOnBtn.visibility = View.VISIBLE
        }
    }

    fun setRandomStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songRandomBtn.visibility = View.VISIBLE
            binding.songRandomOnBtn.visibility = View.GONE
        }
        else {
            binding.songRandomBtn.visibility = View.GONE
            binding.songRandomOnBtn.visibility = View.VISIBLE
        }
    }

    private fun setPlayerStatus(isPlaying: Boolean) {

        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songPlayBtn.visibility = View.GONE
            binding.songPauseBtn.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songPlayBtn.visibility = View.VISIBLE
            binding.songPauseBtn.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true): Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try{
                while (true){
                    if (second >= playTime){
                        break
                    }
                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread{
                            binding.songPlayBar.progress = ((mills / playTime)*100).toInt()
                        }
                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.songStartTime.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }
    //사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songPlayBar.progress * song.playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() //에디터
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)

        editor.apply()
     }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 해제
    }

}