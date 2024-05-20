package com.umc.floclone

data class Locker(
    var title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null,
    var songs: ArrayList<Song>? = null
)
