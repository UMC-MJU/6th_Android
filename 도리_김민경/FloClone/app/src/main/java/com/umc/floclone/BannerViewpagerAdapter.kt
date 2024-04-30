package com.umc.floclone

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerViewpagerAdapter(fragment: Fragment) :FragmentStateAdapter(fragment) {
    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    // 데이터를 몇 개 전달할 것인지 작성하는 함수
    override fun getItemCount(): Int = fragmentlist.size
    // fragment를 생성하는 함수
    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment: Fragment) {
        fragmentlist.add(fragment)  // fragment에 인자값으로 받은 fragment를 추가
        notifyItemInserted(fragmentlist.size-1)
    }
}