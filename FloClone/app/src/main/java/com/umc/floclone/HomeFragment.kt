package com.umc.floclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc.floclone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        return binding.root
    }
}