
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LookaroundViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is lookaround Fragment"
    }
    val text: LiveData<String> = _text
}