package com.example.misscallnotifier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat

class MissedCallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Retrieve information about the missed call
        val phoneNumber = intent?.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

        // Pass the phone number to the notification service
        val notificationIntent = Intent(context, NotificationService::class.java)
        notificationIntent.putExtra("phone_number", phoneNumber)
        ContextCompat.startForegroundService(context!!, notificationIntent)
    }
}
