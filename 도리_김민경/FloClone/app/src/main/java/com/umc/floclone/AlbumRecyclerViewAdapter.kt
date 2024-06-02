package com.umc.floclone

import android.os.Parcel
import android.os.Parcelable
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.umc.floclone.databinding.ItemAlbumBinding

class AlbumRecyclerViewAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
        fun onPlayAlbum(album: Album)
    }

    interface CommunicationInterface {
        fun sendData(album: Album)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun addItem(album: Album) {
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumRecyclerViewAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(albumList[position]) }
        holder.binding.itemAlbumPlayImgIv.setOnClickListener { mItemClickListener.onPlayAlbum(albumList[position]) }
//        holder.binding.itemAlbumTitleTv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) }
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }
}