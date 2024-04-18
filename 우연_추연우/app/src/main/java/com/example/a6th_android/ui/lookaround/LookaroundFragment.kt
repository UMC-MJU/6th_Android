
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a6th_android.databinding.FragmentLookaroundBinding

class LookaroundFragment : Fragment() {

    private var _binding: FragmentLookaroundBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(LookaroundViewModel::class.java)

        _binding = FragmentLookaroundBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textLookaround
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}