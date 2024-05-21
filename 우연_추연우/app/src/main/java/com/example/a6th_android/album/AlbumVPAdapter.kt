package com.example.a6th_android.album

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a6th_android.detail.DetailFragment
import com.example.a6th_android.ui.detail.VideoFragment
import com.example.a6th_android.song.SongFragment

class AlbumVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            //songFragment
            0 -> SongFragment()
            //DetailFragment
            1 ->  DetailFragment()
            //VideoFragment
            else -> VideoFragment()
        }
    }

}