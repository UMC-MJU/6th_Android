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

class MainActivity : AppCompatActivity() {
    lateinit var activityResultLauncher:ActivityResultLauncher<Intent>
    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val handler = Handler(Looper.getMainLooper())
//        //Looper 간단히 메세지 처리를 위해 다른 스레드에서 전달한 메세지를 꺼내줌
//        //main thread에 고유한 루퍼를 가져와서 핸들러로 전달
//
//        val imageList = arrayListOf<Int>()
//        imageList.add(R.drawable.bear)
//        imageList.add(R.drawable.duke)
//        imageList.add(R.drawable.just)
//        Thread{
//            for(image in imageList){
//                handler.post{
//                    binding.iv.setImageResource(image)
//                }//여기서 작성된 코드가 메인스레드로 전달해 메인스레드에서 실행하게함
//                handler 대신 runOnUiThread{ }  //내부코드를 메인에서 돌아가게함
//                Thread.sleep(2000)
//            }
//        }.start()

        binding.mainPlayerCl.setOnClickListener {
            startActivity(Intent(this,SongActivity::class.java))
        }

        setContentView(binding.root)

        initBottomNavigation()

        val song = Song(
            binding.miniPlayerTitleTv.text.toString(),
            binding.miniPlayerSingerTv.text.toString(),
            0,60,false
        )

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
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second",song.second)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("isPlaying",song.isPlaying)
            startActivity(intent)
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
}