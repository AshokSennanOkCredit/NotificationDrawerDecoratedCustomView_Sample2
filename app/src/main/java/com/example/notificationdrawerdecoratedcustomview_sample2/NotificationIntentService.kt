package com.example.customnotificationdrawersample_decoratedcustomviewstylr

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class NotificationIntentService() : IntentService("Notification Intent Service") {
    override fun onHandleIntent(intent: Intent?) {
        when(intent?.action){
             "left" ->{
                 val leftHandler = Handler(Looper.getMainLooper())
                 leftHandler.post {
                     Toast.makeText(this,"You clicked Left Button",Toast.LENGTH_SHORT).show()
                 }
             }
            "right" ->{
                val leftHandler = Handler(Looper.getMainLooper())
                leftHandler.post {
                    Toast.makeText(this,"You clicked Right Button",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}