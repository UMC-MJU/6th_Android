package com.example.memoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memoapp.databinding.ActivityMemoCheckBinding

class MemoCheckActivity : AppCompatActivity() {

    lateinit var binding : ActivityMemoCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemoCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("memo")) {
            binding.memoCheckText.text = intent.getStringExtra("memo")!!
        }
    }
}