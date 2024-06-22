package com.example.myapplication

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemSongBinding

class SavedSongRVAdapter() : RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()
    interface MyitemClickListener {
//        fun onRemoveAlbum(position: Int)
        fun onRemoveSong(songId: Int)

    }
    private lateinit var myitemClickListener: MyitemClickListener

//    fun removeItem(position: Int){
//        albumList.removeAt(position)
//        notifyDataSetChanged()
//    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int) {
        songs.removeAt(position)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged") // 경고 무시 어노테이션
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }
    fun setMyItemClickListener(itemClickListener: MyitemClickListener){
        myitemClickListener = itemClickListener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedSongRVAdapter.ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

//    private val switchStatus = SparseBooleanArray()

    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) {
//        holder.bind(albumList[position])
//        holder.binding.itemSongMoreIv.setOnClickListener{myitemClickListener.onRemoveAlbum(position)}
//
//        val switch = holder.binding.switchRV
//        switch.isChecked = switchStatus[position]
//        switch.setOnClickListener{
//            if (switch.isChecked){
//                switchStatus.put(position, true)
//            }
//            else{
//                switchStatus.put(position,false)
//            }
//            notifyItemChanged(position)
//        }
        holder.bind(songs[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            myitemClickListener.onRemoveSong(songs[position].id) // 좋아요 취소로 업데이트하는 메서드
            removeSong(position) // 현재 화면에서 아이템을 제거
        }
    }

    override fun getItemCount(): Int = songs.size


    inner class ViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song){
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongImgIv.setImageResource(song.coverImg!!)
        }

    }
}



