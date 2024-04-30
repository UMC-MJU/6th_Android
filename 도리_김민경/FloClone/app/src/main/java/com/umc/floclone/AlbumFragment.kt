package com.umc.floclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.floclone.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(layoutInflater)

//        binding.musicListTitle1Tv.text = arguments?.getString("title")
//        binding.musicListSinger1Tv.text = arguments?.getString("singer")
//        binding.albumSingerTv.text = arguments?.getString("singer")
//
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frm, HomeFragment(R.id.home_pannel_cl))
                .commitAllowingStateLoss()
        }
//
//        binding.musicListTitle1Tv.setOnClickListener {
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
//

        val albumAdapter = AlbumViewpagerAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        // TabLayout과 ViewPager2를 연결하는 중재자
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}