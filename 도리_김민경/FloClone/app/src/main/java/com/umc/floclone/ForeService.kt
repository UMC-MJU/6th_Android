package com.umc.floclone

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread

class ForeService: Service() {
    lateinit var mp : MediaPlayer
    private lateinit var builder: NotificationCompat.Builder
    private val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer.create(this, R.raw.music_popcorn)
        mp.isLooping = false
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        mp.start()
        builder = NotificationCompat.Builder(this, Constant.CHANNEL_ID)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showNotification()
        }

        builder.setProgress(100, 0, false)
        manager.notify(Constant.MUSIC_NOTIFICATION_ID, builder.build())

        thread {
            for (i in 1..100) {
                builder.setProgress(100, i, false)
                manager.notify(Constant.MUSIC_NOTIFICATION_ID, builder.build())
            }
        }

        // 시스템이 강제 종료되었을 때 자동으로 다시 시작, 재시작되면 onStartCommand() 호출, Intent에 Null 전달
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        // 알림 클릭했을 때 이동할 화면 지정
        val notificationIntent = Intent(this, SongActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE   // PendingIntent(Intent를 특정 시점에 수행) 변경 불가
        )
        val notification = Notification
            .Builder(this, Constant.CHANNEL_ID)
            .setContentText("음악이 재생중입니다.")
            .setSmallIcon(R.drawable.ic_my_like_on)
            .setContentIntent(pendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(Constant.MUSIC_NOTIFICATION_ID, notification)
        }
    }

    // 알림 채널 생성
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                Constant.CHANNEL_ID, "Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChannel)
        }
    }
}