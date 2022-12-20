package com.udacity

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.content_main.*

private val notId = 0
private val ReqCode = 0
private val flags = 0

fun NotificationManager.sendNotification(
    body: String,
    applicationContext: Context,
    status: String

) {

    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    contentIntent.putExtra("body",body)
    contentIntent.putExtra("status",status)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        notId,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.not_channel_id)
    )
        .setSmallIcon(R.drawable.abc_vector_test)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(body)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .build()

    notify(notId,builder)
    //btn.buttonState=ButtonState.Completed
}

 fun createChannel(app:Application ,id: String, name: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            id,
            name,
            NotificationManager.IMPORTANCE_LOW
        )

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "Time for breakfast"

        val notificationManager = app.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(notificationChannel)

    }


}




