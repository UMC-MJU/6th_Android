
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.a6th_android.MainActivity
import com.example.a6th_android.R
import com.example.a6th_android.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        setFragmentResultListener("TitleInfo") { requestKey, bundle ->
            binding.albumMusicTitleTv.text = bundle.getString("title")
        }
        setFragmentResultListener("SingerInfo") { requestKey, bundle ->
            binding.albumSingerNameTv.text = bundle.getString("singer")
        }

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        binding.songMusicListLl.setOnClickListener {
            Toast.makeText(activity,"title", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}