

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.a6th_android.MainActivity
import com.example.a6th_android.R
import com.example.a6th_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeTodaylaunchAlbumImg01Iv.setOnClickListener {
            setFragmentResult("TitleInfo", bundleOf("title" to binding.homeTodaylaunchAlbumTitle01Tv.text.toString()))
            setFragmentResult("SingerInfo", bundleOf("singer" to binding.homeTodaylaunchAlbumSinger01Tv.text.toString()))

            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }

        binding.homeTodaylaunchAlbumImg02Iv.setOnClickListener {
            setFragmentResult("TitleInfo", bundleOf("title" to binding.homeTodaylaunchAlbumTitle02Tv.text.toString()))
            setFragmentResult("SingerInfo", bundleOf("singer" to binding.homeTodaylaunchAlbumSinger02Tv.text.toString()))

            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }
}