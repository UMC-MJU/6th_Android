package com.example.a6th_android.savedAlbum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a6th_android.databinding.FragmentStorageSavedalbumBinding
import com.example.a6th_android.song.SongDatabase

class SavedAlbumFragment : Fragment() {
    lateinit var binding: FragmentStorageSavedalbumBinding
    lateinit var albumDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorageSavedalbumBinding.inflate(inflater, container, false)

        albumDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.storageSavedAlbumRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val albumRVAdapter = SavedAlbumStorageRVAdapter()
        //리스너 객체 생성 및 전달

        albumRVAdapter.setMyItemClickListener(object : SavedAlbumStorageRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                albumDB.albumDao().getLikedAlbums(getJwt())
            }
        })

        binding.storageSavedAlbumRecyclerView.adapter = albumRVAdapter

        albumRVAdapter.addAlbums(albumDB.albumDao().getLikedAlbums(getJwt()) as ArrayList)
    }

    private fun getJwt() : Int {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)
        val jwt = spf!!.getInt("jwt", 0)
        Log.d("MAIN_ACT/GET_JWT", "jwt_token: $jwt")

        return jwt
    }
}