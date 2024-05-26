
import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a6th_android.album.Album
import com.example.a6th_android.databinding.ItemAlbumBinding
import com.example.a6th_android.databinding.ItemStorageAlbumBinding
import com.example.a6th_android.song.Song

class SavedMusicRVAdapter() :
    RecyclerView.Adapter<SavedMusicRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()

    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedMusicRVAdapter.ViewHolder {
        val binding: ItemStorageAlbumBinding = ItemStorageAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedMusicRVAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemAlbumMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemStorageAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song){
            binding.itemAlbumImgIv.setImageResource(song.coverImg!!)
            binding.itemAlbumTitleTv.text = song.title
            binding.itemAlbumSingerTv.text = song.singer
        }
    }
}