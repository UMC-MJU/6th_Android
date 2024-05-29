package com.example.a6th_android.storage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.a6th_android.BottomSheetFragment
import com.example.a6th_android.MainActivity
import com.example.a6th_android.databinding.FragmentStorageBinding
import com.example.a6th_android.logIn.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.log

class StorageFragment : Fragment() {

    lateinit var binding: FragmentStorageBinding
    private val information = arrayListOf("저장한 곡", "음악파일", "저장 앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStorageBinding.inflate(inflater, container, false)

        val storageAdapter = StorageVPAdapter(this)
        binding.storageContentVp.adapter = storageAdapter

        TabLayoutMediator(binding.storageContentTb, binding.storageContentVp) {
                tab, position -> tab.text = information[position]
        }.attach()

        binding.storageLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun getJwt() : Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0)
    }

    private fun initViews() {
        val jwt : Int = getJwt()
        if(jwt == 0) {
            binding.storageLoginTv.text = "로그인"
            binding.storageLoginTv.setOnClickListener{
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        } else {
            binding.storageLoginTv.text = "로그아웃"
            binding.storageLoginTv.setOnClickListener{
                logOut()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }

    }

    private fun logOut() {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt")
        editor.apply()
    }
}