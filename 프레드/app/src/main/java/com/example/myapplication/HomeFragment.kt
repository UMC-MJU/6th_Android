package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentHomeBinding
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homePannelAlbumBeenzinoIv.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("title", binding.homePannelTodayMusicTitle1Tv.text.toString())
            bundle.putString("singer", binding.homePannelTodayMusicSinger1Tv.text.toString())


            val albumFragment = AlbumFragment()
            albumFragment.arguments = bundle

           (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                R.id.main_frm, albumFragment).commitAllowingStateLoss()

        }
        binding.homePannelAlbumBigbang.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("title", binding.homePannelTodayMusicTitle2Tv.text.toString())
            bundle.putString("singer", binding.homePannelTodayMusicSinger2Tv.text.toString())


            val albumFragment = AlbumFragment()
            albumFragment.arguments = bundle

            (context as MainActivity).supportFragmentManager.beginTransaction().replace(
               R.id.main_frm, albumFragment).commitAllowingStateLoss()

        }

        binding.homePannelAlbumChangmo.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("title", binding.homePannelTodayMusicTitle3Tv.text.toString())
            bundle.putString("singer", binding.homePannelTodayMusicSinger3Tv.text.toString())


            val albumFragment = AlbumFragment()
            albumFragment.arguments = bundle

            (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                R.id.main_frm, albumFragment).commitAllowingStateLoss()

        }

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
}