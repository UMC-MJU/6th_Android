package com.example.a6th_android.album

import com.example.a6th_android.Song

data class Album(
        var title: String? = "",
        var singer: String? = "",
        var coverImg: Int? = null,
        var songs : ArrayList<Song>? = null
)
