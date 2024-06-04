package com.umc.floclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umc.floclone.databinding.FragmentBottomSheetBinding

class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomSheetPlayLayout.setOnClickListener {
            Toast.makeText(requireContext(), "듣기 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.bottomSheetPlaylistLayout.setOnClickListener {
            Toast.makeText(requireContext(), "재생목록 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.bottomSheetMylistLayout.setOnClickListener {
            Toast.makeText(requireContext(), "내 리스트 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.bottomSheetDeleteLayout.setOnClickListener {
            Toast.makeText(requireContext(), "삭제 버튼 클릭", Toast.LENGTH_SHORT).show()
        }
    }
}