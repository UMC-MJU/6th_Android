package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song: Song = Song()
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false, "music")

        binding.mainPlayerCl.setOnClickListener{
            //startActivity(Intent(this, SongActivity::class.java))

            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this,SongActivity::class.java)
            startActivity(intent)
//            val intent = Intent(this, SongActivity::class.java)
//            intent.putExtra("title", song.title)
//            intent.putExtra("singer", song.singer)
//            intent.putExtra("second", song.second)
//            intent.putExtra("playTime", song.playTime)
//            intent.putExtra("isPlaying", song.isPlaying)
//            intent.putExtra("music", song.music)
//            startActivity(intent)
        }
        initBottomNavigation()

        Log.d("Song", song.title + song.singer )

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

    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second*100000)/song.playTime
    }

    override fun onStart() {
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData", null)
//
//        song = if(songJson == null) {
//            Song("라일락", "아이유(IU)", 0, 60, false, "music")
//        } else {
//            gson.fromJson(songJson, Song::class.java)
//        }

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if(songId == 0){
            songDB.SongDao().getSongs(1)
        } else {
            songDB.SongDao().getSongs(songId)
        }

        Log.d("song Id", song.id.toString())

        setMiniPlayer(song)

    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.SongDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.SongDao().insert(
            Song("LILAC", "아이유 (IU)", 0, 240, false, "music", R.drawable.img_album_exp2, false, 1)
        )

        songDB.SongDao().insert(
            Song("Flu", "아이유 (IU)", 0, 240, false, "music", R.drawable.img_album_exp2, false, 1)
        )

        songDB.SongDao().insert(
            Song("BUTTER", "방탄소년단 (BTS)", 0, 220, false, "music", R.drawable.img_album_exp, false, 2)
        )

        songDB.SongDao().insert(
            Song("Boy with Luv", "music_boy", 0, 230, false, "music", R.drawable.img_album_exp, false)
        )

        songDB.SongDao().insert(
            Song("Next Level", "에스파 (AESPA)", 0, 230, false, "music", R.drawable.img_album_exp2, false,3)
        )

        songDB.SongDao().insert(
            Song("Supernova", "에스파 (AESPA)", 0, 230, false, "music", R.drawable.img_album_exp2, false,3)
        )

        songDB.SongDao().insert(
            Song("BBoom BBoom", "모모랜드 (MOMOLAND)", 0, 240, false, "music", R.drawable.img_album_exp, false, 5)
        )

        val _songs = songDB.SongDao().getSongs()
        Log.d("DB data", _songs.toString())
    }
    private fun inputDummyAlbums() {
        val albumDB = AlbumDatabase.getInstance(this)!!
        val albums = albumDB.AlbumDao().getAlbums()

        if (albums.isNotEmpty()) return

        albumDB.AlbumDao().insert(
            Album("IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2)
        )

        albumDB.AlbumDao().insert(
            Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp)
        )

        albumDB.AlbumDao().insert(
            Album("iScreaM Vol.10 : Next Level Remixes", "에스파 (AESPA)", R.drawable.img_album_exp2)
        )

        albumDB.AlbumDao().insert(
            Album("MAP OF THE SOUL : PERSONA", "방탄소년단 (BTS)", R.drawable.img_album_exp)
        )

        albumDB.AlbumDao().insert(
            Album("GREAT!", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp2)
        )

        val _albums = albumDB.AlbumDao().getAlbums()
        Log.d("Album DB data", _albums.toString())
    }
}