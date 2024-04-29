package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumBeenzinoTv.text = arguments?.getString("title")
        binding.albumMusicSingerTv.text = arguments?.getString("singer")


        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()
        //taplayoutmediator는 탭과 뷰페이저를 이어줌.

        return binding.root
        //넥서스 4여서 안되는거였다니... 이런

    //       binding.musicListTitle1Tv.setOnClickListener {
        //    //         binding.musicListTitle1Tv.setOnClickListener {
    //            Toast.makeText(activity, "제목1", Toast.LENGTH_SHORT).show()
    //        }
    //
    //        binding.musicListTitle2Tv.setOnClickListener {
    //            Toast.makeText(activity, "제목2", Toast.LENGTH_SHORT).show()
    //        }
    //
    //        binding.musicListTitle3Tv.setOnClickListener {
    //            Toast.makeText(activity, "제목3", Toast.LENGTH_SHORT).show()
    //        }
    //
    //        binding.musicListTitle4Tv.setOnClickListener {
    //            Toast.makeText(activity, "제목4", Toast.LENGTH_SHORT).show()
    //        }
    //
    //        binding.musicListTitle5Tv.setOnClickListener {
    //            Toast.makeText(activity, "제목5", Toast.LENGTH_SHORT).show()
    //        }
    }


}