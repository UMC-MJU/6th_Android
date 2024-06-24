package com.example.umcflo.ui.main.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment:Fragment) :FragmentStateAdapter(fragment) {
    //하나의 리스트를 만들어 여러개의 프래그먼트를 담아둠.
    //이 클래스 안에만 사용할 변수.
    //private로 안써주면 홈프래그먼트에서 데이터 변경이 일어날 수도 있기 때문에
    private val fragmentList : ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int = fragmentList.size


    override fun createFragment(position: Int): Fragment = fragmentList[position]
        //fragment리스트 안의 item들을 생성해주는 함수


    //처음엔 프래그먼트리스트에 아무것도 없기때문에
    fun addFragment(fragment: Fragment){
        //fragment리스트의 인자값으로 받은것들 추가
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size-1)
        //새로운 값이 리스트에 추가 되는 곳을 말하는데 처음들어가는 곳은 index[0]에 들어가고 list의 사이즈는 1이므로

    }



}