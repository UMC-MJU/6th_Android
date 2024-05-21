
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a6th_android.album.Album
import com.example.a6th_android.databinding.ItemStorageAlbumBinding

class SavedMusicRVAdapter(private val albumList: ArrayList<Album>) : RecyclerView.Adapter<SavedMusicRVAdapter.ViewHolder>(){

    // 클릭 인터페이스 정의
    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun removeAlbum(position: Int)
    }

    // 리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
    fun removeInfo(position : Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedMusicRVAdapter.ViewHolder {
        val binding: ItemStorageAlbumBinding = ItemStorageAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedMusicRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(albumList[position]) }
        holder.binding.itemAlbumMoreIv.setOnClickListener { mItemClickListener.removeAlbum(position)}

        holder.binding.itemAlbumPlayIv.setOnClickListener {
            holder.binding.itemAlbumPlayIv.visibility = View.GONE
            holder.binding.itemAlbumPauseIv.visibility = View.VISIBLE
        }

        holder.binding.itemAlbumPauseIv.setOnClickListener {
            holder.binding.itemAlbumPlayIv.visibility = View.VISIBLE
            holder.binding.itemAlbumPauseIv.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemStorageAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumImgIv.setImageResource(album.coverImg!!)
        }
    }
}