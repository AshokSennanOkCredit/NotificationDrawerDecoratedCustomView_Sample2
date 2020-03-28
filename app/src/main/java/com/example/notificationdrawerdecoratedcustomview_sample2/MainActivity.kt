package com.example.customnotificationdrawersample_decoratedcustomviewstylr

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.notificationdrawerdecoratedcustomview_sample2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val NOTIFICATION_TITLE = "Notification Sample App"
    private val CONTENT_TEXT = "Expand me to see a detailed message!"

    private val ANDROID_CHANNEL_ID = "com.example.customnotificationdrawersample_decoratedcustomviewstylr1.ANDROID";
    private val ANDROID_CHANNEL_NAME = "ANDROID CHANNEL1";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            sendNotification()
        }
    }

    private fun sendNotification() {
        val expandView = RemoteViews(packageName,R.layout.view_expand_notification)
        expandView.setTextViewText(R.id.timestamp,DateUtils.formatDateTime(this,System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME))

        val leftIntent = Intent(this,NotificationIntentService::class.java)
        leftIntent.setAction("left")
        expandView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getService(this, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT))


        val rightIntent = Intent(this, NotificationIntentService::class.java)
        rightIntent.setAction("right")
        expandView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getService(this, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT))

        val collapseView = RemoteViews(packageName,R.layout.view_collapsed_notification)
        collapseView.setTextViewText(R.id.timestamp,DateUtils.formatDateTime(this,System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME))


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(ANDROID_CHANNEL_ID,ANDROID_CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            val builder = NotificationCompat.Builder(this,ANDROID_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(CONTENT_TEXT)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this,0,Intent(this,MainActivity::class.java),0))
                .setCustomContentView(collapseView)
                .setCustomBigContentView(expandView)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())

            notificationManager.notify(0, builder.build())
        } else {
            val builder = NotificationCompat.Builder(this)
                // these are the three things a NotificationCompat.Builder object requires at a minimum
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(CONTENT_TEXT)
                // notification will be dismissed when tapped
                .setAutoCancel(true)
                // tapping notification will open MainActivity
                .setContentIntent(PendingIntent.getActivity(this, 0,  Intent(this, MainActivity::class.java), 0))
                // setting the custom collapsed and expanded views
                .setCustomContentView(collapseView)
                .setCustomBigContentView(expandView)
                // setting style to DecoratedCustomViewStyle() is necessary for custom views to display
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())

            // retrieves android.app.NotificationManager
            notificationManager.notify(0, builder.build());
        }

    }

}
