package com.umc.floclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.umc.floclone.databinding.FragmentLockerSongSavedBinding
import com.umc.floclone.databinding.ItemLockerBinding

class SavedSongLockerFragment: Fragment() {
    lateinit var binding: FragmentLockerSongSavedBinding
    lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSongSavedBinding.inflate(layoutInflater)

        // Database에 있는 정보 가져오기
        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.lockerSavedSongRv.layoutManager = LinearLayoutManager(requireActivity())
        val lockerAlbumRecyclerViewAdapter = LockerRecyclerViewAdapter()

        lockerAlbumRecyclerViewAdapter.setMyItemClickListener(object : LockerRecyclerViewAdapter.MyItemClickListener {
            override fun onRemoveLocker(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })

        binding.lockerSavedSongRv.adapter = lockerAlbumRecyclerViewAdapter
        lockerAlbumRecyclerViewAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }
}