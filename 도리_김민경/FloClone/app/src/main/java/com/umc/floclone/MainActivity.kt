package com.umc.floclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.umc.floclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FloClone)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        val song = Song(
            binding.miniPlayerTitleTv.text.toString(),
            binding.miniPlayerSingerTv.text.toString()
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

        binding.miniPlayerLayout.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            activityResultLauncher.launch(intent)
        }
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
}
