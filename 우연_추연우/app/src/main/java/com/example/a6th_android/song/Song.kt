package com.example.a6th_android.song

import androidx.room.*

@Entity(tableName = "SongTable")
data class Song(
    var title: String = "",
    var singer: String = "",
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    var coverImg: Int? = null,
    var isLike: Boolean = false
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
