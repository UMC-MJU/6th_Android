package com.umc.floclone

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.umc.floclone.databinding.FragmentHomeBinding
import me.relex.circleindicator.CircleIndicator3
import java.net.NoRouteToHostException
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class HomeFragment(homePannelCl: Int) : Fragment(), AlbumRecyclerViewAdapter.CommunicationInterface {
    lateinit var binding: FragmentHomeBinding
    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var albumDB: SongDatabase

    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        albumDB =SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(albumDB.albumDao().getAlbums())


        // 데이터 리스트 생성 더미 데이터
//        albumDatas.apply {
//            add(Album("How?", "BOYNEXTDOOR", R.drawable.img_boynextdoor_album))
//            add(Album("성장", "D.O.", R.drawable.img_do_album))
//            add(Album("Fourever", "데이식스 (DAY6)", R.drawable.img_day6_album))
//            add(Album("Armageddon", "에스파 (aespa)", R.drawable.img_aespa_album))
//        }

        val albumRecyclerViewAdapter = AlbumRecyclerViewAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRecyclerViewAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRecyclerViewAdapter.setMyItemClickListener(object: AlbumRecyclerViewAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                (context as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frm, AlbumFragment().apply {
                    // Bundle 이용해서 데이터 전달
                    arguments = Bundle().apply {
                        val gson = Gson()
                        val albumJson = gson.toJson(album)
                        putString("album", albumJson)
                    }
                })
                .commitAllowingStateLoss()
            }

            override fun onRemoveAlbum(position: Int) {
                albumRecyclerViewAdapter.removeItem(position)
            }

            override fun onPlayAlbum(album: Album) {
                sendData(album)
            }
        })

        val bannerAdapter = BannerViewpagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        val homeAdapter = HomeViewpagerAdapter(this)
        homeAdapter.addFragment(HomePannelFragment(R.drawable.img_first_album_default))
        homeAdapter.addFragment(HomePannelFragment(R.drawable.img_first_album_default))
        homeAdapter.addFragment(HomePannelFragment(R.drawable.img_first_album_default))

        binding.homePannelVp.adapter = homeAdapter
        binding.homePannelVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Indicator에 ViewPager 설정
        binding.homePannelCi.setViewPager(binding.homePannelVp)

        startAutoSlide(homeAdapter)


        return binding.root
    }

    private fun startAutoSlide(adapter: HomeViewpagerAdapter) {
        // 3초마다 슬라이드 변경
        timer.scheduleAtFixedRate(3000, 3000) {
            handler.post {
                val nextItem = binding.homePannelVp.currentItem + 1
                if (nextItem < adapter.itemCount) {
                    binding.homePannelVp.currentItem = nextItem
                } else {
                    binding.homePannelVp.currentItem = 0    // 마지막 페이지 -> 첫 페이지
                }
            }
        }
    }

    override fun sendData(album: Album) {
        if (activity is MainActivity) {
            val activity = activity as MainActivity
            activity.updateMiniPlayer(album)
        }
    }
}