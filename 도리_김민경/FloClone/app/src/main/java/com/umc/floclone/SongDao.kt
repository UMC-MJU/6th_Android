package com.umc.floclone

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDao {
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    // 리스트에 있는 Song 가져오기
    @Query("SELECT * FROM SongTable")
    fun getSongs(): List<Song>

    @Query("SELECT * FROM SongTable WHERE id = :id")
    fun getSong(id: Int): Song

    @Query("UPDATE SongTable SET isLike = :isLike WHERE id = :id")
    fun updateIsLikeById(isLike: Boolean, id: Int)

    @Query("SELECT * FROM SongTable WHERE isLike = :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>

//    @Query("SELECT * FROM SongTable WHERE albumIdx = :albumIdx")
//    fun getSongsInAlbum(albumIdx: Int): List<Song>
}