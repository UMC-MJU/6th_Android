package com.umc.floclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.floclone.databinding.FragmentHomeBinding
import com.umc.floclone.databinding.FragmentHomePannelBinding

class HomePannelFragment(val bgRes : Int) : Fragment() {
    lateinit var binding: FragmentHomePannelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePannelBinding.inflate(inflater, container, false)
        val homeViewpagerAdapter = HomeViewpagerAdapter(this)
        binding.homePannelCl.setBackgroundResource(bgRes)
        return binding.root
    }
}