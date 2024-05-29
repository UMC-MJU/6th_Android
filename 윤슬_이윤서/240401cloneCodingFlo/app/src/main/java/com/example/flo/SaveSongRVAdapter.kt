package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.SaveSongRVAdapter.*
import com.example.flo.databinding.ItemSavesongBinding

class SaveSongRVAdapter (private val songList : ArrayList<Song>) : RecyclerView.Adapter<SaveSongRVAdapter.ViewHolder>() {
    interface MyItemClickListener {
        fun onItemClick(song: Song)
        fun onRemoveSong(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun addSongs(song: Song) {
        songList.add(song)
        notifyDataSetChanged() //데이터가 바뀌었음을 알려줌
    }

    fun removeSong(position: Int) {
        songList.removeAt(position)
        notifyDataSetChanged() //데이터가 바뀌었음을 알려줌
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSavesongBinding = ItemSavesongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
//        holder.bind(albumList[position])
//        holder.itemView.setOnClickListener{ mItemClickListener.onItemClick(albumList[position]) }
    }

    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding: ItemSavesongBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSsAlbumTitleTv.text = song.title
            binding.itemSsAlbumSingerTv.text = song.singer
            binding.itemSsAlbumCoverImgIv.setImageResource(song.coverImg!!)
        }
    }

}