package com.example.a6th_android.home

import AlbumFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.widget.ViewPager2
import com.example.a6th_android.MainActivity
import com.example.a6th_android.R
import com.example.a6th_android.databinding.FragmentHomeBinding
import com.example.a6th_android.banner.BannerFragment
import com.example.a6th_android.banner.BannerVPAdapter
import com.example.a6th_android.pannel.PannelFragment
import com.example.a6th_android.pannel.PannelVPAdapter

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeTodaylaunchAlbumImg01Iv.setOnClickListener {
            setFragmentResult(
                "TitleInfo",
                bundleOf("title" to binding.homeTodaylaunchAlbumTitle01Tv.text.toString())
            )
            setFragmentResult(
                "SingerInfo",
                bundleOf("singer" to binding.homeTodaylaunchAlbumSinger01Tv.text.toString())
            )

            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }

        binding.homeTodaylaunchAlbumImg02Iv.setOnClickListener {
            setFragmentResult(
                "TitleInfo",
                bundleOf("title" to binding.homeTodaylaunchAlbumTitle02Tv.text.toString())
            )
            setFragmentResult(
                "SingerInfo",
                bundleOf("singer" to binding.homeTodaylaunchAlbumSinger02Tv.text.toString())
            )

            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }

        val pannelAdapter = PannelVPAdapter(this)
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_first_album_default))
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_album_exp3))
        pannelAdapter.addFragment(PannelFragment(R.drawable.img_album_exp4))

        binding.homePannelBackgroundViewPager.adapter = pannelAdapter
        binding.homePannelBackgroundViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundViewPager)

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerViewPager.adapter = bannerAdapter
        binding.homeBannerViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

}
