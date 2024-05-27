package com.example.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDao {
    //메소드에 접근하기 위한 기능 추가
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    @Query("SELECT * FROM SongTable")
    fun getSongs():List<Song> // list에 저장되어 있는 song 모두 받아오기

    @Query("SELECT * FROM SongTable WHERE id = :id")
    fun getSong(id: Int): Song

    @Query("UPDATE SongTable SET isLike= :isLike WHERE id = :id")
    fun updateIsLikeById(isLike: Boolean,id: Int)

    @Query("SELECT * FROM SongTable WHERE isLike= :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>
}