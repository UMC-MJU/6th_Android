package com.example.umcflo

import com.example.umcflo.data.entities.Album

interface CommunicationInterface {
    fun sendData(album: Album)
}
