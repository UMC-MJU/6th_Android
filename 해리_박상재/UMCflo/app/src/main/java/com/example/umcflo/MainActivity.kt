package com.example.umcflo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.umcflo.databinding.ActivityMainBinding
<<<<<<< Updated upstream
import com.google.gson.Gson
=======
>>>>>>> Stashed changes

class MainActivity : AppCompatActivity() {
    lateinit var activityResultLauncher:ActivityResultLauncher<Intent>
    lateinit var  binding: ActivityMainBinding
<<<<<<< Updated upstream
    private  var song:Song = Song()
    private  var gson: Gson = Gson()

=======
>>>>>>> Stashed changes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_UMCflo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

<<<<<<< Updated upstream
=======
        val song = Song(
            binding.miniPlayerTitleTv.text.toString(),
            binding.miniPlayerSingerTv.text.toString(),
            0,60,false
        )

>>>>>>> Stashed changes
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val message = data.getStringExtra("message")
                    Log.d("message", message!!)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
<<<<<<< Updated upstream
            intent.putExtra("music", song.music)
=======
>>>>>>> Stashed changes
            activityResultLauncher.launch(intent)
        }


    }

    private fun initBottomNavigation(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
<<<<<<< Updated upstream

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime
    }

    //activity 전환이 될땐 onCreate가아닌 start부터 시작
    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //song은 sharedPreferences의 이름
        val songJson = sharedPreferences.getString("songData", null) //songData는 sharedPreferences안에 데이터

        song = if(songJson == null){ //데이터 저장값이 없을때 디폴트값
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        }else{ //songJson을 자바객체로 변환
            gson.fromJson(songJson, Song::class.java)
        }
        setMiniPlayer(song)

    }


=======
>>>>>>> Stashed changes
}