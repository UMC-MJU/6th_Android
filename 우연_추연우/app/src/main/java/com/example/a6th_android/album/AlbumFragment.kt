package com.example.a6th_android.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.a6th_android.MainActivity
import com.example.a6th_android.R
import com.example.a6th_android.album.Album
import com.example.a6th_android.album.AlbumVPAdapter
import com.example.a6th_android.databinding.FragmentAlbumBinding
import com.example.a6th_android.home.HomeFragment
import com.example.a6th_android.like.Like
import com.example.a6th_android.song.SongDatabase
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {

    lateinit var binding : FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")
    private var gson : Gson = Gson()

    private var isLiked : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)

        isLiked = isLikedAlbum(album.id)
        setInit(album)
        setOnClickListeners(album)


        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
            tab, position ->
            tab.text = information[position]
        }.attach()


        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
        if(isLiked) {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt() : Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0)
    }

    private fun likeAlbum(userId : Int, albunId : Int) {
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId,albunId)
        songDB.albumDao().likeAlbum(like)
    }

    private fun disLikeAlbum(albumId : Int) {
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId :Int? = songDB.albumDao().disLikeAlbum(userId, albumId)
    }

    private fun isLikedAlbum(albumId : Int) : Boolean {
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId :Int? = songDB.albumDao().isLikeAlbum(userId, albumId)

        return likeId != null
    }

    private fun setOnClickListeners(album : Album) {
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if(isLiked) {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikeAlbum(album.id)
            } else {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }
    }
}