package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var dateText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dateText = findViewById(R.id.date)
        val btn:Button = findViewById<Button>(R.id.ageInput)
        btn.setOnClickListener() {
            pickDate()
        }

    }

    fun pickDate() {
        var cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
//        DatePickerDialog(this,
////            DatePickerDialog.OnDateSetListener(){view, selectedYear, selectedMonth,selectedDay ->
////                val selectedDate = "$selectedDay/${selectedMonsth + 1}/$selectedYear"
////                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
////                val formattedDate = sdf.format(sdf.parse(selectedDate))
////                dateText?.text= formattedDate.toString()
//            },
//            year,
//            month,
//            day
//        ).show()



        //TODO:setmaxdate
    }
}