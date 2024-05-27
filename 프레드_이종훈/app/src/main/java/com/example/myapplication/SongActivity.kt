package com.example.myapplication

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.media.AsyncPlayer
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private  var gson: Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()
//        setPlayer(songs[nowPos])

//        var title : String? = null
//        var singer : String? = null
//
//        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
//            title = intent.getStringExtra("title")
//            singer = intent.getStringExtra("singer")
//            binding.musicTitleTv.text = title
//            binding.singerNameTv.text = singer
//        }




    }
    //사용자가 포커스를 잃었을 때 음악이 중지.
    override fun onPause() {
        super.onPause()
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)
        //song데이터 내부 저장소 저장 작업
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() // 에디터를 만들어야만 간단한 데이터를 저장하는 shared 함수 사용가능
        //editor.putString("title", song.title)
        //editor.putString("singer",song.singer)
        //일일히 다 넣어주려면 귀찮고 복잡하기에 Gson을 사용해 전달
        editor.putInt("songId",songs[nowPos].id)
        editor.putInt("second",songs[nowPos].second)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null // 미디어 플레이어 해제
    }
    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener(){
        binding.lowerIb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", songs[nowPos].title + "_" + songs[nowPos].singer)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(true)
            startStopService()
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
            startStopService()
        }
        binding.songNextIv.setOnClickListener {
            moveSong(+1)

        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)

        }
        binding.playerLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }

    }
    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if (!isLike){
            binding.playerLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.playerLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

    }

    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        Log.d("now Song ID", songs[nowPos].id.toString())

//        if(intent.hasExtra("title") && intent.hasExtra("singer")){
//            song = Song(
//                intent.getStringExtra("title")!!,
//                intent.getStringExtra("singer")!!,
//                intent.getIntExtra("second", 0),
//                intent.getIntExtra("playTime",0),
//                intent.getBooleanExtra("isPlaying",false),
//                intent.getStringExtra("music")!!
//
//            )
//        }
        startTimer()
        setPlayer(songs[nowPos])
    }
    private fun moveSong(direct: Int){
        if (nowPos + direct < 0) {
            Toast.makeText(this,"first song",Toast.LENGTH_SHORT).show()
            return
        }

        else if (nowPos + direct >= songs.size){
            Toast.makeText(this,"last song",Toast.LENGTH_SHORT).show()
            return
        }else{
        nowPos += direct
        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
        }


    }
    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song : Song){
        binding.musicTitleTv.text = song.title
        binding.singerNameTv.text = song.singer
        binding.playerMusicStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.playerMusicEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songAlbumBeenzinoIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw",this.packageName)
        mediaPlayer = MediaPlayer.create(this,music)
        if(song.isLike) {
            binding.playerLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }
        else {
            binding.playerLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying
        if(isPlaying){
            binding.songPauseIv.visibility = View.VISIBLE
            binding.songMiniplayerIv.visibility = View.GONE
            mediaPlayer?.start()

        }

        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
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
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
        timer.start()
    }
    private fun startStopService() {
        if (isServiceRunning(ForegroundService::class.java)) {
            Toast.makeText(this, "Foreground Service Stopped", Toast.LENGTH_SHORT).show()
            stopService(Intent(this, ForegroundService::class.java))
        }
        else {
            Toast.makeText(this, "Foreground Service Started", Toast.LENGTH_SHORT).show()
            startService(Intent(this, ForegroundService::class.java))
        }
    }
    private fun isServiceRunning(inputClass : Class<ForegroundService>) : Boolean {
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