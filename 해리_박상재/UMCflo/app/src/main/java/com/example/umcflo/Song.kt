package com.example.umcflo

data class Song (
    val title: String = "",
    val singer: String = "",
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,

    //어떤음악이 재생되어야하는지 알려주는
    var music: String = ""

)