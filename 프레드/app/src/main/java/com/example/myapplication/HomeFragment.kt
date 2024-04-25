package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

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
        bannerAdapter.addFragment(BannerFragment())
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL



        return binding.root
    }
}