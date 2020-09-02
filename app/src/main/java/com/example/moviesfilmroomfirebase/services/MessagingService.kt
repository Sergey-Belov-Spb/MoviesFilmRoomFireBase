package com.example.moviesfilmroomfirebase.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.moviesfilmroomfirebase.MainActivity
import com.example.moviesfilmroomfirebase.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService(){
    companion object {
        const val TAG = "MessagingService"
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var idMovie:String = ""
        for ((key, value) in remoteMessage.data.entries) {
            Log.e("Test", "Key = $key, Value = $value")
            if (key == "idNewMovie") {
                idMovie = value
            }
        }

        Log.e(TAG, "collapsekey: " + remoteMessage.collapseKey)
        Log.e(TAG, "from: " + remoteMessage.from)
        Log.e(TAG, "message id: " + remoteMessage.messageId)
        Log.e(TAG, "message type:: " + remoteMessage.messageType)
        Log.e(TAG, "to: " + remoteMessage.to)
        Log.e(TAG, "send time: " + remoteMessage.sentTime)
        Log.e(TAG, "ttl: " + remoteMessage.ttl)
        remoteMessage.notification?.let {
            Log.e(TAG, "title: " + it.title)
            Log.e(TAG, "body: " + it.body)
            Log.e(TAG, "click action: " + it.clickAction)
            Log.e(TAG, "color: " + it.color)
            Log.e(TAG, "icon: " + it.icon)
        }
        sendNotification(remoteMessage,idMovie)

    }

    private fun sendNotification(remoteMessage: RemoteMessage,idMovie:String) {

        val notificationChannelId = getString(R.string.default_notification_channel_id)

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (nm.getNotificationChannel(notificationChannelId) == null) {
                NotificationChannel(notificationChannelId, "Notifications", NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "Notifications from firebase"
                    enableLights(true)
                    enableVibration(true)
                    setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
                    nm.createNotificationChannel(this)
                }
            }
        }
        val intent = Intent( this, MainActivity::class.java)
        intent.putExtra("idMovie", idMovie)
        //intent.putExtra("picMovie", picMovie);
        //intent.putExtra("picMovie", picMovie);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("Появился новый фильм")

            .setAutoCancel( true )
            .setChannelId( notificationChannelId )
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_stat_notification, "Смотреть фильм", pendingIntent)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)


        val notificationId = System.currentTimeMillis().toInt()
        nm.notify(notificationId, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        Log.d(TAG, token)
    }

}