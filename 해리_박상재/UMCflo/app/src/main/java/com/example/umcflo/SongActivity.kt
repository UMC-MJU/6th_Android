package com.example.umcflo

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
import com.example.umcflo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var song: Song
    //전역변수로 스레드 하나를 생성하고 아래에 송객체를 초기화하며 타이머 객채를 생성
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

//        var title : String? = null
//        var singer : String? = null
//
//
//        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
////            title = intent.getStringExtra("title")
////            singer = intent.getStringExtra("singer")
////            binding.musicTitleTv.text = title
////            binding.singerNameTv.text = singer
//            binding.musicTitleTv.text = intent.getStringExtra("title")!!
//            binding.singerNameTv.text = intent.getStringExtra("singer")!!
//
//        }

        binding.lowerIb.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("message", title + " _ " + singer)
//            setResult(RESULT_OK, intent)
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        //진한색의 drawable이 없음 다운받아야함
//        binding.playerRepeatInactiveIv.setOnClickListener {
//            setRepeatStatus(true)
//        }
//
//        binding.playerRepeatActiveIv.setOnClickListener {
//            setRepeatStatus(false)
//        }

    }


    private  fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!! ,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.musicTitleTv.text = intent.getStringExtra("title")!!
        binding.singerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime%60)
        binding.songProgressSb.progress = if (song.playTime == 0) 0 else (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songPauseIv.visibility = View.VISIBLE
            binding.songMiniplayerIv.visibility = View.GONE
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

//    private fun setRepeatStatus(isRepeat: Boolean) {
//        if (isRepeat) {     // 전체 반복이 아닐 경우
//            binding.playerRepeatInactiveIv.visibility = View.GONE
//            binding.playerRepeatActiveIv.visibility = View.VISIBLE
//        } else {    // 전체 반복일 경우
//            binding.playerRepeatInactiveIv.visibility = View.VISIBLE
//            binding.playerRepeatActiveIv.visibility = View.GONE
//        }
//    }
    
    private fun  startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {
        private var second : Int = 0
        private var mills : Float = 0F

        override fun run() {
            super.run()

            try {
                while(true) {
                    if(second >= playTime) {
                        break
                    }

                    while (!isPlaying) {
                        sleep(200) // 0.2초 대기
                    }

                    if(isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            // binding.songProgressSb.progress = ((mills/playTime*1000) * 100).toInt()
                            binding.songProgressSb.progress = ((mills/playTime) * 100).toInt()
                        }

                        if(mills % 1000 == 0F) { // 1초
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("SongActivity", "Thread Terminates! ${e.message}")
            }
        }
    }
}