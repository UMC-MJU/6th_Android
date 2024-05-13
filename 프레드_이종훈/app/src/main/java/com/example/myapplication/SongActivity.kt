package com.example.myapplication

import android.content.Intent
import android.media.AsyncPlayer
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivitySongBinding
import com.google.gson.Gson
import java.util.Timer

class SongActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySongBinding.inflate(layoutInflater)
    }
    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private  var gson: Gson = Gson()
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
    //사용자가 포커스를 잃었을 때 음악이 중지.
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000
        //song데이터 내부 저장소 저장 작업
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() // 에디터를 만들어야만 간단한 데이터를 저장하는 shared 함수 사용가능
        //editor.putString("title", song.title)
        //editor.putString("singer",song.singer)
        //일일히 다 넣어주려면 귀찮고 복잡하기에 Gson을 사용해 전달
        val songJson = gson.toJson(song) // song객체를 gson포멧으로 변환
        editor.putString("songData", songJson)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null // 미디어 플레이어 해제
    }
    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying",false),
                intent.getStringExtra("music")!!

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
        val music = resources.getIdentifier(song.music, "raw",this.packageName)
        mediaPlayer = MediaPlayer.create(this,music)
        setPlayerStatus(song.isPlaying)


    }

    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying
        if(isPlaying){
            binding.songPauseIv.visibility = View.GONE
            binding.songMiniplayerIv.visibility = View.VISIBLE
            mediaPlayer?.start()

        }

        else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            if(mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
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