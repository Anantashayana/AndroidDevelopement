package com.example.sendsms

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    //val REQUEST_CALL_PHONE_PERMISSION = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cal = findViewById<Button>(R.id.call)
        val msg = findViewById<Button>(R.id.message)
        val mail = findViewById<Button>(R.id.mail)

        cal.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                call()
            }
        })
        msg.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                message()
            }
        })
        mail.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                mail()
            }
        })
    }
    @SuppressLint("SuspiciousIndentation")
    fun call() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+917483201544"))
        //intent.data = Uri.parse("tel:+917483201544")
        startActivity(intent)
    }
    fun message() {
        val phoneNumber = "1234567890"
        val message = "Hello, this is a test message"
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phoneNumber")
            putExtra("sms_body", message)
        }
        startActivity(intent)
    }
    fun mail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:anantashayana.hegde@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Sub:Leave")
        intent.putExtra(Intent.EXTRA_TEXT, "Iam absent")
        startActivity(intent)
    }
}