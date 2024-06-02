package com.example.a6th_android.user

import androidx.room.*

@Entity(tableName = "UserTable")
data class User (
    var email : String = "",
    var password : String = ""
) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0;
}
