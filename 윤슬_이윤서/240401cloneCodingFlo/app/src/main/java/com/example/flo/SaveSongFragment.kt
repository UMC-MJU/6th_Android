package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSaveSongBinding

class SaveSongFragment : Fragment() {

    lateinit var binding : FragmentSaveSongBinding
    private var savesongData = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveSongBinding.inflate(inflater,container, false)


        //데이터 리스트 생성 더미 데이터
        savesongData.apply {
//            add(Album("Lilac", "아이유(IU)", R.drawable.img_album_exp2))
//            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//            add(Album("Next Level", "에스파(AESPA)", R.drawable.img_album_exp2))
//            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp))
        }

        val SaveSongRVAdapter = SaveSongRVAdapter(savesongData)
        binding.homeSsMusicAlbumRv.adapter = SaveSongRVAdapter
        binding.homeSsMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        return binding.root
    }
}