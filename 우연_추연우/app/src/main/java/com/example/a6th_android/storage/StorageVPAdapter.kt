package com.example.a6th_android.storage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a6th_android.musicFile.MusicFileFragment
import com.example.a6th_android.savedAlbum.SavedAlbumFragment
import com.example.a6th_android.savedMusic.SavedMusicFragment

class StorageVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            //SavedMusicFragment
            0 -> SavedMusicFragment()
            //MusicFileFragment
            1 -> MusicFileFragment()
            else -> SavedAlbumFragment()
        }
    }

}