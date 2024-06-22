package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")
    private var gson: Gson = Gson()
    private var isLiked : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        val userId = getJwt()
        isLiked = isLikedAlbum(album.id)
        setInit(album)


//        binding.albumBeenzinoTv.text = arguments?.getString("title")
//        binding.albumMusicSingerTv.text = arguments?.getString("singer")
//        val imageResourceId = arguments?.getInt("album")
//        if (imageResourceId != null) {
//            binding.albumBeenzinoIv.setImageResource(imageResourceId)
//        }
        binding.albumLikeIv.setOnClickListener {
            if(isLiked) {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikeAlbum(album.id)
            }

            else {
                binding.albumLikeIv.setImageResource((R.drawable.ic_my_like_on))
                likeAlbum(userId, album.id)
            }
        }




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

    private fun setInit(album: Album){
        binding.albumBeenzinoIv.setImageResource(album.coverImg!!)
        binding.albumBeenzinoTv.text = album.title.toString()
        binding.albumMusicSingerTv.text=album.singer.toString()
    }

    private fun getJwt() : Int {
        val spf = requireActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("jwt", 0)
    }

    private fun likeAlbum(userId : Int, albumId : Int) {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }


    private fun isLikedAlbum(albumId : Int) : Boolean {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val userId = getJwt()

        val likeId : Int? = songDB.albumDao().isLikedAlbum(userId, albumId)
        return likeId != null
    }

    private fun disLikeAlbum(albumId : Int) {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val userId = getJwt()

        songDB.albumDao().dislikedAlbum(userId, albumId)
    }


}