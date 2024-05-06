package com.umc.floclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umc.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

//        var title : String? = null
//        var singer : String? = null

//        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
//            title = intent.getStringExtra("title")
//            singer = intent.getStringExtra("singer")
//            binding.playerMusicTitleTv.text = title
//            binding.playerMusicSingerTv.text = singer
//        }

        // 아래 화살표 버튼 누르면 액티비티 종료
        binding.playerDownIb.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("message", title + " _ " + singer)
//            setResult(RESULT_OK, intent)
            finish()
        }

        // 재생 상태 변경
        binding.playerPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.playerPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        // 반복 변경
        binding.playerRepeatInactiveIv.setOnClickListener {
            setRepeatStatus(true)
        }

        binding.playerRepeatActiveIv.setOnClickListener {
            setRepeatStatus(false)
        }
    }

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 60000),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun setPlayer(song: Song) {
        binding.playerMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.playerMusicSingerTv.text = intent.getStringExtra("singer")!!
        binding.playerMusicStartTimeTv.text =
            String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.playerMusicEndTimeTv.text =
            String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress =
            if (song.playTime == 0) 0 else (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying) {    // 재생중일 경우
            binding.playerPlayIv.visibility = View.GONE
            binding.playerPauseIv.visibility = View.VISIBLE
        } else {    // 일시정지한 경우
            binding.playerPlayIv.visibility = View.VISIBLE
            binding.playerPauseIv.visibility = View.GONE
        }
    }

    private fun setRepeatStatus(isRepeat: Boolean) {
        if (isRepeat) {     // 전체 반복이 아닐 경우
            binding.playerRepeatInactiveIv.visibility = View.GONE
            binding.playerRepeatActiveIv.visibility = View.VISIBLE
        } else {    // 전체 반복일 경우
            binding.playerRepeatInactiveIv.visibility = View.VISIBLE
            binding.playerRepeatActiveIv.visibility = View.GONE
        }
    }

    private fun startTimer() {
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {
        private var second: Int = 0
        private var millis: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (second >= playTime) {
                        break
                    }

                    if (isPlaying) {
                        sleep(50)
                        millis += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((millis / playTime) * 100).toInt()
                        }

                        if (millis % 1000 == 0f) {
                            runOnUiThread {
                                binding.playerMusicStartTimeTv.text =
                                    String.format("%02d:%02d", song.second / 60, song.second % 60)
                            }
                        }
                        second++
                    }
                }

            } catch (e: InterruptedException) {
                Log.d("Song", "스레드가 죽었습니다. ${e.message}")
            }
        }
    }

}
//    override fun onBackPressed() {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra("message", "뒤로가기 버튼 클릭")
//        setResult(RESULT_OK, intent)
//        finish()
//    }