package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentLockerSavedsongBinding

class SavedSongFragment : Fragment() {

    lateinit var songDB: SongDatabase
    lateinit var binding: FragmentLockerSavedsongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)

//        albumDatas.apply {
//            add(Album("아쿠아맨", "빈지노(BEENZINO)", R.drawable.aquaman))
//            add(Album("뱅뱅뱅", "빅뱅 (BIGBANG)", R.drawable.bangbangbang))
//            add(Album("아름다워", "창모 (CHANGMO)", R.drawable.beautiful))
//            add(Album("밤양갱", "비비 (BIBI)", R.drawable.bibi_album))
//            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//            add(Album("아쿠아맨", "빈지노(BEENZINO)", R.drawable.aquaman))
//            add(Album("뱅뱅뱅", "빅뱅 (BIGBANG)", R.drawable.bangbangbang))
//            add(Album("아름다워", "창모 (CHANGMO)", R.drawable.beautiful))
//            add(Album("밤양갱", "비비 (BIBI)", R.drawable.bibi_album))
//            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//        }
//
//        val lockerAlbumRVAdapter = SavedSongRVAdapter(albumDatas)
//        binding.lockerSavedSongRv.adapter = lockerAlbumRVAdapter
//        binding.lockerSavedSongRv.layoutManager = LinearLayoutManager(requireActivity())
//
//        lockerAlbumRVAdapter.setMyItemClickListener(object:SavedSongRVAdapter.MyitemClickListener{
//            override fun onRemoveAlbum(position: Int) {
//                lockerAlbumRVAdapter.removeItem(position)
//            }
//        })
        songDB = SongDatabase.getInstance(requireContext())!!
//
        return binding.root
//    }
    }
    private fun initRecyclerview() {
        binding.lockerSavedSongRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = SavedSongRVAdapter()

        songRVAdapter.setMyItemClickListener(object : SavedSongRVAdapter.MyitemClickListener {
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }

        })

        binding.lockerSavedSongRv.adapter = songRVAdapter

        songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }
}