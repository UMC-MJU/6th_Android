package com.example.a6th_android.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a6th_android.BottomSheetFragment
import com.example.a6th_android.databinding.FragmentStorageBinding
import com.google.android.material.tabs.TabLayoutMediator

class StorageFragment : Fragment() {

    lateinit var binding: FragmentStorageBinding
    private val information = arrayListOf("저장한 곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStorageBinding.inflate(inflater, container, false)

        val storageAdapter = StorageVPAdapter(this)
        binding.storageContentVp.adapter = storageAdapter

        TabLayoutMediator(binding.storageContentTb, binding.storageContentVp) {
                tab, position ->
            tab.text = information[position]
        }.attach()

        val bottomSheetFragment = BottomSheetFragment()
        binding.storageSelectAllTv.setOnClickListener {
            bottomSheetFragment.show(requireFragmentManager(),"BottomSheetDialog")
        }

        return binding.root
    }

}