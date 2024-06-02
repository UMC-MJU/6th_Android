import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flo.Album
import com.example.flo.AlbumDAO

@Database(entities = [Album::class], version = 1)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun AlbumDao(): AlbumDAO

    companion object {
        private var instance: AlbumDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : AlbumDatabase? {
            if(instance == null) {
                synchronized(AlbumDatabase::class) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AlbumDatabase::class.java,
                        "album-database"
                    ).allowMainThreadQueries().build().also { instance = it }
                }
            }
            return instance
        }
    }
}