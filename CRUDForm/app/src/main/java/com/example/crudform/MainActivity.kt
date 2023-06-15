package com.example.crudform

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var contact: EditText
    private lateinit var dob: EditText
    private lateinit var insert: Button
    private lateinit var update: Button
    private lateinit var delete: Button
    private lateinit var view: Button
    private lateinit var dB: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.name)
        contact = findViewById(R.id.contact)
        dob = findViewById(R.id.dob)
        insert = findViewById(R.id.btnInsert)
        update = findViewById(R.id.btnUpdate)
        delete = findViewById(R.id.btnDelete)
        view = findViewById(R.id.btnView)
        dB = DBHelper(this)

        insert.setOnClickListener {
            val nameTXT = name.text.toString()
            val contactTXT = contact.text.toString()
            val dobTXT = dob.text.toString()
            val checkinsertdata = dB.insertuserdata(nameTXT, contactTXT, dobTXT)
            if (checkinsertdata == true)
                Toast.makeText(this, "New Entry Inserted", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show()
        }

        update.setOnClickListener {
            val nameTXT = name.text.toString()
            val contactTXT = contact.text.toString()
            val dobTXT = dob.text.toString()
            val checkupdatedata = dB.updateuserdata(nameTXT, contactTXT, dobTXT)
            if (checkupdatedata == true)
                Toast.makeText(this, "Entry Updated", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "New Entry Not Updated", Toast.LENGTH_SHORT).show()
        }

        delete.setOnClickListener {
            val nameTXT = name.text.toString()
            val checkudeletedata = dB.deletedata(nameTXT)
            if (checkudeletedata == true)
                Toast.makeText(this, "Entry Deleted", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Entry Not Deleted", Toast.LENGTH_SHORT).show()
        }

        view.setOnClickListener {
            val res: Cursor = dB.getdata()
            if (res.getCount() == 0) {
                Toast.makeText(this, "No Entry Exists", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val buffer = StringBuffer()
            while (res.moveToNext()) {
                buffer.append("Name :" + res.getString(0) + "\n")
                buffer.append("Contact :" + res.getString(1) + "\n")
                buffer.append("Date of Birth :" + res.getString(2) + "\n\n")
            }
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setTitle("User Entries")
            builder.setMessage(buffer.toString())
            builder.show()
        }
    }
}