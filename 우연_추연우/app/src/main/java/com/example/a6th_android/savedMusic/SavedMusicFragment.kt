package com.example.a6th_android.savedMusic

import AlbumFragment
import SavedMusicRVAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a6th_android.MainActivity
import com.example.a6th_android.R
import com.example.a6th_android.album.Album
import com.example.a6th_android.databinding.FragmentStorageSavedalbumBinding
import com.google.gson.Gson

class SavedMusicFragment : Fragment() {

    lateinit var binding: FragmentStorageSavedalbumBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorageSavedalbumBinding.inflate(inflater, container, false)

        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스트 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Moy with LUV", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        val savedMusicRVAdapter = SavedMusicRVAdapter(albumDatas)

        binding.savedMusicAlbumRv.adapter = savedMusicRVAdapter
        binding.savedMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        savedMusicRVAdapter.setMyItemClickListener(object : SavedMusicRVAdapter.MyItemClickListener{

            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun removeAlbum(position: Int) {
                savedMusicRVAdapter.removeInfo(position)
            }
        })

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            }).commitAllowingStateLoss()
    }
}