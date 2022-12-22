package com.betano.planner

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat

class MyReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("TAG","RECEIVE")
        var notif = p0!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var ch = NotificationChannel("id","name",NotificationManager.IMPORTANCE_DEFAULT)
        notif.createNotificationChannel(ch)
        var n = Notification.BigTextStyle(
            Notification.Builder(p0,"id")
                .setSmallIcon(R.mipmap.notif_foreground)
                .setContentTitle("Betano Planner")
                .setContentText(p1!!.getStringExtra("text"))
                .setContentIntent(PendingIntent.getActivity(p0,0,Intent(p0,SplashActivity::class.java),PendingIntent.FLAG_MUTABLE)))
            .setBigContentTitle("Betano Planner")
            .setSummaryText(p1!!.getStringExtra("text"))
            .build()
        notif.notify(n.hashCode(),n)
    }
}