package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.util.ArrayList
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment(), Communication {

    lateinit var binding: FragmentHomeBinding
    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())
    private var albumDatas = ArrayList<Album>()
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())
        Log.d("albumlist", albumDatas.toString())

//        albumDatas.apply {
//            add(Album("아쿠아맨","빈지노(BEENZINO)",R.drawable.aquaman))
//            add(Album("뱅뱅뱅","빅뱅",R.drawable.bangbangbang))
//            add(Album("아름다워","창모",R.drawable.beautiful))
//        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                //albumfragment로 전환되는 코드 사용
                changeAlbumFragment(album)
                //recyclerview의 item을 클릭했을때 앨범 프래그먼트로 전환됨.

            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

            override fun onPlayAlbum(album: Album) {
                sendData(album)
            }
        } )

//        binding.homePannelAlbumBeenzinoIv.setOnClickListener {
//            val imageResourceId : Int = R.drawable.aquaman
//
//
//            val bundle = Bundle()
//            bundle.putString("title", binding.homePannelTodayMusicTitle1Tv.text.toString())
//            bundle.putString("singer", binding.homePannelTodayMusicSinger1Tv.text.toString())
//            bundle.putInt("album",imageResourceId)
//            val albumFragment = AlbumFragment()
//            albumFragment.arguments = bundle
//
//           (context as MainActivity).supportFragmentManager.beginTransaction().replace(
//                R.id.main_frm, albumFragment).commitAllowingStateLoss()
//
//
//
//
//
//
//        }
//        binding.homePannelAlbumBigbang.setOnClickListener {
//            val imageResourceId:Int = R.drawable.bangbangbang
//
//            val bundle = Bundle()
//            bundle.putString("title", binding.homePannelTodayMusicTitle2Tv.text.toString())
//            bundle.putString("singer", binding.homePannelTodayMusicSinger2Tv.text.toString())
//            bundle.putInt("album",imageResourceId)
//
//
//            val albumFragment = AlbumFragment()
//            albumFragment.arguments = bundle
//
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(
//               R.id.main_frm, albumFragment).commitAllowingStateLoss()
//
//        }
//
//        binding.homePannelAlbumChangmo.setOnClickListener {
//            val imageResourceId:Int = R.drawable.beautiful
//            val bundle = Bundle()
//            bundle.putString("title", binding.homePannelTodayMusicTitle3Tv.text.toString())
//            bundle.putString("singer", binding.homePannelTodayMusicSinger3Tv.text.toString())
//            bundle.putInt("album",imageResourceId)
//
//
//            val albumFragment = AlbumFragment()
//            albumFragment.arguments = bundle
//
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(
//                R.id.main_frm, albumFragment).commitAllowingStateLoss()
//
//        }

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val pannelAdpater = PannelVpAdapter(this)
        pannelAdpater.addFragment(PannelFragment(R.drawable.img_first_album_default))
        pannelAdpater.addFragment(PannelFragment(R.drawable.skplanet_musicmate_sc0_2021_03_12_16_59_19))
        pannelAdpater.addFragment(PannelFragment(R.drawable._786635_719636_923))

        binding.homePannelBackgroundIv.adapter = pannelAdpater
        binding.homePannelBackgroundIv.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.homePannelBackgroundIv)

        autoSlide(pannelAdpater)




        return binding.root
    }

    override fun sendData(album: Album) {
        if (activity is MainActivity){
            val activity = activity as MainActivity
            activity.updateMainPlayerCl(album)
        }
    }
    private fun changeAlbumFragment(album: Album){
        (context as MainActivity).supportFragmentManager.beginTransaction().replace(
        R.id.main_frm, AlbumFragment().apply {
            arguments = Bundle().apply {
                val gson = Gson()
                val albumJson = gson.toJson(album)
                putString("album", albumJson)

            }
        }).commitAllowingStateLoss()
        //recyclerview의 item을 클릭했을때 앨범 프래그먼트로 전환됨.




}


    private fun autoSlide(adapter: PannelVpAdapter) {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    val nextItem = binding.homePannelBackgroundIv.currentItem + 1
                    if (nextItem < adapter.itemCount) {
                        binding.homePannelBackgroundIv.currentItem = nextItem
                    } else {
                        binding.homePannelBackgroundIv.currentItem = 0 // 순환
                    }
                }
            }
        }, 3000, 3000)
    }
//    private fun inputDummyAlbums(){
//        val songDB = SongDatabase.getInstance(requireActivity())!!
//        val songs = songDB.albumDao().getAlbums()
//
//        if (songs.isNotEmpty()) return
//
//        songDB.albumDao().insert(
//            Album(
//                1,
//                "IU 5th Album 'LILAC'",
//                "아이유 (IU)",
//                R.drawable.img_album_exp2
//            )
//        )
//
//        songDB.albumDao().insert(
//            Album(
//                2,
//                "Butter",
//                "방탄소년단 (BTS)",
//                R.drawable.img_album_exp
//            )
//        )
//
//        songDB.albumDao().insert(
//            Album(
//                3,
//                "iScreaM Vol.10: Next Level Remixes",
//                "에스파 (AESPA)",
//                R.drawable.img_album_exp
//            )
//        )
//
//        songDB.albumDao().insert(
//            Album(
//                4,
//                "Map of the Soul Persona",
//                "뮤직 보이 (Music Boy)",
//                R.drawable.img_album_exp,
//            )
//        )
//
//
//        songDB.albumDao().insert(
//            Album(
//                5,
//                "Great!",
//                "모모랜드 (MOMOLAND)",
//                R.drawable.img_album_exp
//            )
//        )
//
//        val songDBData = songDB.albumDao().getAlbums()
//        Log.d("DB data", songDBData.toString())
//    }
}