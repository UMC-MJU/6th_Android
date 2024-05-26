package com.example.a6th_android.savedMusic

import SavedMusicRVAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a6th_android.databinding.FragmentStorageSavedalbumBinding
import com.example.a6th_android.song.Song
import com.example.a6th_android.song.SongDatabase

class SavedMusicFragment : Fragment() {

    lateinit var songDB: SongDatabase
    lateinit var binding : FragmentStorageSavedalbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorageSavedalbumBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root

    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.savedMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity())
        val lockerAlbumRVAdapter = SavedMusicRVAdapter()

        lockerAlbumRVAdapter.setMyItemClickListener(object : SavedMusicRVAdapter.MyItemClickListener {

            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })
        binding.savedMusicAlbumRv.adapter = lockerAlbumRVAdapter
        lockerAlbumRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }
}