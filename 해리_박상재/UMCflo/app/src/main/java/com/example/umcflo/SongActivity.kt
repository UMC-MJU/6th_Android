package com.example.umcflo

import android.content.Intent
import android.media.AsyncPlayer
import android.media.Image
<<<<<<< Updated upstream
import android.media.MediaPlayer
=======
>>>>>>> Stashed changes
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umcflo.databinding.ActivitySongBinding
<<<<<<< Updated upstream
import com.google.gson.Gson
=======
>>>>>>> Stashed changes

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var song: Song
    //전역변수로 스레드 하나를 생성하고 아래에 송객체를 초기화하며 타이머 객채를 생성
    lateinit var timer: Timer
<<<<<<< Updated upstream
    //미디어파일 재생 ?뜻은 null값이 들어올 수도 있다는 의미(해제용)
    private  var mediaPlayer: MediaPlayer? = null
    private  var gson: Gson = Gson()
=======
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
=======
                intent.getBooleanExtra("isPlaying", false)
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        //리소스 파일에서 해당 스트링 값으로 파일로 받아오기 위함
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
=======
>>>>>>> Stashed changes

        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songPauseIv.visibility = View.VISIBLE
            binding.songMiniplayerIv.visibility = View.GONE
<<<<<<< Updated upstream
            mediaPlayer?.start()
=======
>>>>>>> Stashed changes
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
<<<<<<< Updated upstream
            if(mediaPlayer?.isPlaying == true){ //미디어플레이가 재생중이 아닐때 오류발생 방지
                mediaPlayer?.pause()
            }
=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        mediaPlayer?.release() // 미디어 플레이어가 갖고있던 리소스 해제
        mediaPlayer = null // 미디어 플레이어 해제

    }


=======
    }

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    //사용자가 포커스를 잃었을때 음악이 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        //저장된 시간부터 시작
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000
        //어플 종료후에도 저장된 데이터를 사용하기위함(간단한데이터)
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() //에디터

//        editor.putString("title", song.title)
//        editor.putString("title", song.singer)
    // -> json(데이터 표현 표준)을 이용해 한번에 보냄
        //Gson:  자바객체<->Json
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)
        editor.apply() //실제저장공간에 저장
    }
=======
>>>>>>> Stashed changes
}