package com.example.umcflo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umcflo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
        fun onPlayAlbum(album : Album)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener ) {
        this.itemClickListener = onItemClickListener
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()  //리사이클은 데이터가 바뀐걸 모르기에 알려줌
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()  //리사이클은 데이터가 바뀐걸 모르기에 알려줌
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
        // 뷰 홀더를 생성해줘야 할 때 호출되는 함수로, 아이템 뷰 객체를 만들어서 뷰홀더에 전달한다.
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        // 뷰홀더에 데이터를 바인딩해줘야 할 때마다 호출되는 함수로 자주 호출된다.
        holder.bind(albumList[position])

        //클릭리스너가 내장되있지 않기에 추가로 인터페이스를 만들어야함
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(albumList[position])
        }

        holder.binding.itemAlbumPlayImgIv.setOnClickListener {
            itemClickListener.onPlayAlbum(albumList[position])
        }


        //title누를 시 삭제하게
//        holder.binding.itemAlbumTitleTv.setOnClickListener {
//            itemClickListener.onRemoveAlbum(position)
//        }

    }

    override fun getItemCount(): Int = albumList.size
        // 데이터 세트 크기를 알려주는 함수 => 리사이클러뷰가 마지막이 언제인지를 알게 된다.



    //날라가지 않도록하는 그릇
    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImage!!)
        }
    }

}