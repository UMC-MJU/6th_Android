package com.umc.floclone

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.floclone.databinding.ItemLockerBinding
import java.util.ArrayList

class LockerRecyclerViewAdapter(private val albumList: ArrayList<Locker>): RecyclerView.Adapter<LockerRecyclerViewAdapter.ViewHolder>() {
    // 리사이클러뷰 스위치 오류 해결
    private val switchStatus = SparseBooleanArray()

    interface MyItemClickListener {
        fun onRemoveLocker(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun removeItem(position: Int) {
        albumList.removeAt(position)
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
        holder.bind(albumList[position])
        holder.binding.lockerAlbumMoreIv.setOnClickListener { mItemClickListener.onRemoveLocker(position) }

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

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemLockerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(locker: Locker) {
            binding.lockerAlbumTitleTv.text = locker.title
            binding.lockerAlbumSingerTv.text = locker.singer
            binding.lockerAlbumImgIv.setImageResource(locker.coverImg!!)
        }
    }
}