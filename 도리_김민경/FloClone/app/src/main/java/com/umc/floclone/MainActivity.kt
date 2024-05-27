package com.umc.floclone

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.umc.floclone.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var timer: SongActivity.Timer
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private var song: Song = Song()
    private var gson: Gson = Gson()

    val songs = arrayListOf<Song>()
    var nowPos = 0
    lateinit var songDB: SongDatabase

//    val albums = arrayListOf<Album>()
//    lateinit var albumDB: AlbumDatabase

    // 음악 재생을 위한 mediaPlayer
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FloClone)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        initMiniPlayList()

//        binding.miniPlayerPlayIv.setOnClickListener {
//            val intent = Intent(this, ForeService::class.java)
//            ContextCompat.startForegroundService(this, intent)
//        }

        initBottomNavigation()

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

        binding.miniPlayerLayout.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            activityResultLauncher.launch(intent)
        }

        binding.miniPlayerNextIv.setOnClickListener {
            moveSong(+1)
        }

        binding.miniPlayerPreviousIv.setOnClickListener {
            moveSong(-1)
        }
    }

    override fun onPause() {
        super.onPause()
        songs[nowPos].second = (binding.mainProgressSb.progress * songs[nowPos].playTime) / 100000
        songs[nowPos].isPlaying = false
        setMiniPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("songId", songs[nowPos].id)

        editor.apply()
    }

    private fun initBottomNavigation() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frm, HomeFragment(R.id.home_pannel_cl))
            .commitAllowingStateLoss()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_navi_home_no_select -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, HomeFragment(R.id.home_pannel_cl))
                        .commitAllowingStateLoss()

                    return@setOnItemSelectedListener true
                }

                R.id.bottom_navi_look_no_select -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()

                    return@setOnItemSelectedListener true
                }

                R.id.bottom_navi_search_no_select -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()

                    return@setOnItemSelectedListener true
                }

                R.id.bottom_navi_locker_no_select -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()

                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song: Song) {
        binding.miniPlayerTitleTv.text = song.title
        binding.miniPlayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second * 100000) / song.playTime

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
    }

    private fun setMiniPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying) {    // 재생중일 경우
            binding.miniPlayerPlayIv.visibility = View.GONE
            binding.miniPlayerPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {            // 일시정지한 경우
            binding.miniPlayerPlayIv.visibility = View.VISIBLE
            binding.miniPlayerPauseIv.visibility = View.GONE
            // mediaPlayer은 재생 중이 아닐 때 pause를 하면 오류가 발생할 수 있음
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    // Song Activity의 데이터 반영은 onStart에서 해주는 것이 좋음
    // onResume에서 해줘도 되지만 onStart가 사용자에게 액티비티 보이기 직전에 실행되므로 더 안정적
    override fun onStart() {
        super.onStart()

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        // SharedPreferences에 저장된 값이 0이라면 첫번째 인덱스
        // SongDatabase의 인스턴스 호출
        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0) {       // 재생 중인 노래가 없으면
            songDB.songDao().getSong(1)
        } else {        // 재생중인 노래가 있으면
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())

        // 미니플레이어에 반영
        setMiniPlayer(song)
    }

    fun updateMiniPlayer(album: Album) {
        binding.miniPlayerTitleTv.text = album.title
        binding.miniPlayerSingerTv.text = album.singer
        binding.mainProgressSb.progress = 0
    }

//    private fun inputDummyAlbums() {
//        val albumDB = AlbumDatabase.getInstance(this)!!
//        val albums = albumDB.albumDao()
//    }

    // DB에 데이터 없으면 데이터 넣어주기
    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        // songs에 데이터가 이미 존재하면 더미 데이터를 삽입할 필요가 없음
        if (songs.isNotEmpty()) return

        // songs에 데이터가 없을 경우 더미 데이터를 삽입
        songDB.songDao().insert(
            Song(
                "라일락 (Lilac)",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_iu",
                R.drawable.img_album_exp2,
                false,

            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_iu",
                R.drawable.img_album_exp2,
                false,

                )
        )

        songDB.songDao().insert(
            Song(
                "Earth, Wind & Fire",
                "BOYNEXTDOOR",
                0,
                210,
                false,
                "music_ewf",
                R.drawable.img_boynextdoor_album,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Amnesia",
                "BOYNEXTDOOR",
                0,
                210,
                false,
                "music_ewf",
                R.drawable.img_boynextdoor_album,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Popcorn",
                "D.O.",
                0,
                220,
                false,
                "music_popcorn",
                R.drawable.img_do_album,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Mars",
                "D.O.",
                0,
                220,
                false,
                "music_popcorn",
                R.drawable.img_do_album,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Welcom to the Show",
                "데이식스 (Day6)",
                0,
                230,
                false,
                "music_day6",
                R.drawable.img_day6_album,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Happy",
                "데이식스 (Day6)",
                0,
                230,
                false,
                "music_day6",
                R.drawable.img_day6_album,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Armageddon",
                "에스파 (aespa)",
                0,
                240,
                false,
                "music_armageddon",
                R.drawable.img_aespa_album,
                false,
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

    private fun initMiniPlayList() {
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun moveSong(direct: Int) {
        if (nowPos + direct < 0) {
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        } else if (nowPos + direct >= songs.size) {
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        } else {
            nowPos += direct

            mediaPlayer?.release()
            mediaPlayer = null

            setMiniPlayer(songs[nowPos])
        }
    }

//    private fun inputDummyAlbums() {
//        val albumDB = AlbumDatabase.getInstance(this)!!
//        val albums = albumDB.albumDao().getAlbums()
//
//        if (albums.isNotEmpty()) return
//
//        albumDB.albumDao().insert(
//            Album(
//                "IU 5th Album 'LILAC'",
//                "아이유 (IU)",
//                R.drawable.img_album_exp2,
//            )
//        )
//
//        albumDB.albumDao().insert(
//            Album(
//                "How?",
//                "BOYNEXTDOOR",
//                R.drawable.img_boynextdoor_album,
//            )
//        )
//
//        albumDB.albumDao().insert(
//            Album(
//                "성장",
//                "D.O.",
//                R.drawable.img_do_album,
//            )
//        )
//
//        albumDB.albumDao().insert(
//            Album(
//                "Fourever",
//                "데이식스 (Day6)",
//                R.drawable.img_day6_album,
//            )
//        )
//
//        albumDB.albumDao().insert(
//            Album(
//                "Armageddon",
//                "에스파 (aespa)",
//                R.drawable.img_aespa_album,
//            )
//        )
//    }
}
