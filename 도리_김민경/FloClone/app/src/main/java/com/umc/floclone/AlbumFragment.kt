package com.umc.floclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.umc.floclone.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        // 변경된 데이터 받아오기
        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
//        val albumDB = AlbumDatabase.getInstance(this)!!
//        val album = albumDB.albumDao().getAlbum(albumIdx)
        setInit(album)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frm, HomeFragment(R.id.home_pannel_cl))
                .commitAllowingStateLoss()
        }

        val albumAdapter = AlbumViewpagerAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        // TabLayout과 ViewPager2를 연결하는 중재자
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumNameTv.text = album.title.toString()
        binding.albumSingerTv.text = album.singer.toString()

    }
}