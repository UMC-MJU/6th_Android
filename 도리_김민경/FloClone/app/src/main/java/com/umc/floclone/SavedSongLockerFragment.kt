package com.umc.floclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.umc.floclone.databinding.FragmentLockerSongSavedBinding
import com.umc.floclone.databinding.ItemLockerBinding

class SavedSongLockerFragment: Fragment() {
    lateinit var binding: FragmentLockerSongSavedBinding

    private var albumDatas = ArrayList<Locker>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSongSavedBinding.inflate(layoutInflater)

        // 데이터 리스트 생성 더미 데이터
        albumDatas.apply {
            add(Locker("How?", "BOYNEXTDOOR", R.drawable.img_boynextdoor_album))
            add(Locker("성장", "D.O.", R.drawable.img_do_album))
            add(Locker("Fourever", "데이식스 (DAY6)", R.drawable.img_day6_album))
            add(Locker("Armageddon", "에스파 (aespa)", R.drawable.img_aespa_album))
            add(Locker("How?", "BOYNEXTDOOR", R.drawable.img_boynextdoor_album))
            add(Locker("성장", "D.O.", R.drawable.img_do_album))
            add(Locker("Fourever", "데이식스 (DAY6)", R.drawable.img_day6_album))
            add(Locker("Armageddon", "에스파 (aespa)", R.drawable.img_aespa_album))
            add(Locker("How?", "BOYNEXTDOOR", R.drawable.img_boynextdoor_album))
            add(Locker("성장", "D.O.", R.drawable.img_do_album))
            add(Locker("Fourever", "데이식스 (DAY6)", R.drawable.img_day6_album))
            add(Locker("Armageddon", "에스파 (aespa)", R.drawable.img_aespa_album))
            add(Locker("How?", "BOYNEXTDOOR", R.drawable.img_boynextdoor_album))
            add(Locker("성장", "D.O.", R.drawable.img_do_album))
            add(Locker("Fourever", "데이식스 (DAY6)", R.drawable.img_day6_album))
            add(Locker("Armageddon", "에스파 (aespa)", R.drawable.img_aespa_album))
        }

        val lockerRecyclerViewAdapter = LockerRecyclerViewAdapter(albumDatas)
        binding.lockerSavedSongRv.adapter = lockerRecyclerViewAdapter
        binding.lockerSavedSongRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        lockerRecyclerViewAdapter.setMyItemClickListener(object : LockerRecyclerViewAdapter.MyItemClickListener {
            override fun onRemoveLocker(position: Int) {
                lockerRecyclerViewAdapter.removeItem(position)
            }
        })

        return binding.root
    }
}