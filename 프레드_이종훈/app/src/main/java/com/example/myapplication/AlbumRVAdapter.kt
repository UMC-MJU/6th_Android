package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>):RecyclerView.Adapter<AlbumRVAdapter.viewHolder>(){

    interface MyItemClickListener{
        fun onItemClick(album: Album) //프로퍼티를 album으로
        fun onRemoveAlbum(position: Int)

        fun onPlayAlbum(album: Album)
    }

    private  lateinit var  myItemClickListener: MyItemClickListener
    //외부에서 함수 선언

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }
    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.viewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.viewHolder, position: Int) {
        //recyclerview에서 id = Position
        holder.bind(albumList[position])
        //positIon값을 여기서 가지고 있으므로 여기서 click이벤트 처리
        holder.itemView.setOnClickListener{myItemClickListener.onItemClick(albumList[position]) }
        holder.binding.itemAlbumTitleTv.setOnClickListener{ myItemClickListener.onRemoveAlbum(position)}

        holder.binding.itemAlbumPlayImgIv.setOnClickListener{
            myItemClickListener.onPlayAlbum(albumList[position])
        }
    }

    override fun getItemCount(): Int = albumList.size
    inner class viewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        //itemview객체가 날라가지 않도록 담아두는 그릇
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}
