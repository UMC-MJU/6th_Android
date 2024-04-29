package com.umc.floclone

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.umc.floclone.databinding.FragmentHomeBinding
import me.relex.circleindicator.CircleIndicator3
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class HomeFragment(homePannelCl: Int) : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.homePannelAlbum1.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title", binding.homePannelTodayMusicTitle1Tv.text.toString())
            bundle.putString("singer", binding.homePannelTodayMusicSinger1Tv.text.toString())

            val albumFragment = AlbumFragment()
            albumFragment.arguments = bundle

            (context as MainActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frm, albumFragment)
                .commitAllowingStateLoss()
        }

        val bannerAdapter = BannerViewpagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

//        val homeAdapter = HomeViewpagerAdapter(this)
//        binding.homePannelVp.adapter = homeAdapter

//        val homeViewpagerAdapter = HomeViewpagerAdapter(this)
//        homeViewpagerAdapter.addFragment(HomePannelFragment(R.layout.fragment_home_pannel))
//        // Indicator에 ViewPager 설정
//        binding.homePannelCi.setViewPager(binding.homePannelVp)

        return binding.root
    }

//    private fun startAutoSlide(adapter: BannerViewpagerAdapter) {
//        // 3초마다 슬라이드 변경
//        timer.scheduleAtFixedRate(3000, 3000) {
//            handler.post {
//                val nextItem = binding.homePannelVp.currentItem + 1
//                if (nextItem < adapter.itemCount) {
//                    binding.homePannelVp.currentItem = nextItem
//                } else {
//                    binding.homePannelVp.currentItem = 0    // 마지막 페이지 -> 첫 페이지
//                }
//            }
//        }
//    }
}