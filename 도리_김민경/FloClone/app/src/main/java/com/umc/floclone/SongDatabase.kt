package com.umc.floclone

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class], version = 1)    // entities: 데이터베이스에서 관리할 테이블 정의, version: 데이터베이스의 버전
abstract class SongDatabase: RoomDatabase() {       // RoomDatabase 상속 받음(무조건 abstract class로 정의되어야 함)
    abstract fun songDao(): SongDao

    // singleton pattern 구현
    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"     // 다른 데이터베이스랑 이름 겹치지 않도록 주의
                    ).allowMainThreadQueries().build()  // 편의상 메인 스레드에서 작업
                }
            }
            return instance
        }
    }
}