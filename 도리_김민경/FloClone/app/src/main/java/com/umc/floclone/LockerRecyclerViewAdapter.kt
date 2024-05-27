package com.umc.floclone

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.floclone.databinding.ItemLockerBinding
import java.util.ArrayList

class LockerRecyclerViewAdapter(): RecyclerView.Adapter<LockerRecyclerViewAdapter.ViewHolder>() {
    // 리사이클러뷰 스위치 오류 해결
    private val switchStatus = SparseBooleanArray()
    private val songs = ArrayList<Song>()

    interface MyItemClickListener {
        fun onRemoveLocker(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    private fun removeSong(position: Int) {
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LockerRecyclerViewAdapter.ViewHolder {
        val binding: ItemLockerBinding = ItemLockerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.lockerAlbumMoreIv.setOnClickListener {
            mItemClickListener.onRemoveLocker(songs[position].id)   // 좋아요 취소
            removeSong(position)      // 현재 화면에서 position에 해당하는 아이템 제거
        }

        val switch = holder.binding.switchUser
        switch.isChecked = switchStatus[position]
        switch.setOnClickListener {
            if (switch.isChecked) {
                switchStatus.put(position, true)
            } else {
                switchStatus.put(position, false)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    inner class ViewHolder(val binding: ItemLockerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.lockerAlbumTitleTv.text = song.title
            binding.lockerAlbumSingerTv.text = song.singer
            binding.lockerAlbumImgIv.setImageResource(song.coverImg!!)
        }
    }
}