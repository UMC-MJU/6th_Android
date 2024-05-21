package com.umc.floclone

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.umc.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer
    // 음악 재생을 위한 mediaPlayer
    private var mediaPlayer: MediaPlayer? = null
    // GSON 선언
    val gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        // 아래 화살표 버튼 누르면 액티비티 종료
        binding.playerDownIb.setOnClickListener {
            finish()
        }

        // 재생 상태 변경
        binding.playerPlayIv.setOnClickListener {
            setPlayerStatus(true)
            // ForegroundService 설정
            startStopService()
        }

        binding.playerPauseIv.setOnClickListener {
            setPlayerStatus(false)
            // ForegroundService 설정
            startStopService()
        }

        // 반복 변경
        binding.playerRepeatInactiveIb.setOnClickListener {
            setRepeatStatus(true)
        }

        binding.playerRepeatActiveIb.setOnClickListener {
            setRepeatStatus(false)
        }
    }

    private fun startStopService() {
        if (isServiceRunning(ForeService::class.java)) {
            Toast.makeText(this, "Foreground Service Stopped", Toast.LENGTH_SHORT).show()
            stopService(Intent(this, ForeService::class.java))
        } else {
            Toast.makeText(this, "Foreground Service Started", Toast.LENGTH_SHORT).show()
            startService(Intent(this, ForeService::class.java))
        }
    }

    private fun isServiceRunning(inputClass: Class<ForeService>) : Boolean {
        val manager : ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager

        for (service : ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (inputClass.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
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

    private fun setPlayer(song: Song) {
        binding.playerMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.playerMusicSingerTv.text = intent.getStringExtra("singer")!!
        binding.playerMusicStartTimeTv.text =
            String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.playerMusicEndTimeTv.text =
            String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = if (song.playTime == 0) 0 else (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying) {    // 재생중일 경우
            binding.playerPlayIv.visibility = View.GONE
            binding.playerPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {    // 일시정지한 경우
            binding.playerPlayIv.visibility = View.VISIBLE
            binding.playerPauseIv.visibility = View.GONE
            // mediaPlayer은 재생 중이 아닐 때 pause를 하면 오류가 발생할 수 있음
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    private fun setRepeatStatus(isRepeat: Boolean) {
        if (isRepeat) {     // 전체 반복이 아닐 경우
            binding.playerRepeatInactiveIb.visibility = View.GONE
            binding.playerRepeatActiveIb.visibility = View.VISIBLE
        } else {    // 전체 반복일 경우
            binding.playerRepeatInactiveIb.visibility = View.VISIBLE
            binding.playerRepeatActiveIb.visibility = View.GONE
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
                                    String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }

            } catch (e: InterruptedException) {
                Log.d("Song", "스레드가 죽었습니다. ${e.message}")
            }
        }
    }

    // 사용자가 포커스를 잃었을 때 음악이 중지됨
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime) / 100) / 1000  // 밀리초로 계산되고 있어 1000으로 나누어줘야함

        // 내부 저장소에 데이터 저장, 앱 종료 후 재실행시 저장된 데이터 불러옴
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()   // 에디터

        // 일일이 putString을 하지 않고 JSON으로 사용 가능
//        editor.putString("title", song.title)
//        editor.putString("title", song.singer)

        // song 객체를 JSON 포맷으로 변환
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)

        // 실제 저장공간에 저장
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()  // 미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null     // 미디어플레이어 해제
    }
}