package com.example.fetchjsondata

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var fetchButton: Button
    private lateinit var listView: ListView
    private lateinit var contactList: ArrayList<HashMap<String, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contactList = ArrayList()
        listView = findViewById(R.id.list)
        fetchButton = findViewById(R.id.fetch)
        fetchButton.setOnClickListener {
            val strUrl = "https://raw.githubusercontent.com/wellingtoncosta/fake-contacts-api/master/db.json"
            Thread {
                val json_response = makeServiceCall(strUrl)
                if (json_response != null) {
                    try {
                        val jsonObj = JSONObject(json_response)
                        val contacts = jsonObj.getJSONArray("contacts")
                        for (i in 0 until contacts.length()) {
                            val c = contacts.getJSONObject(i)
                            val id = c.getString("id")
                            val name = c.getString("name")
                            val email = c.getString("email")
                            val contact = HashMap<String, String>()
                            contact["id"] = id
                            contact["name"] = name
                            contact["email"] = email
                            contactList.add(contact)
                        }
                        Handler(Looper.getMainLooper()).post {
                            val adapter = SimpleAdapter(
                                this, contactList,
                                R.layout.list_item, arrayOf("id", "name", "email"),
                                intArrayOf(R.id.cid, R.id.cname, R.id.cemail)
                            )
                            listView.adapter = adapter
                        }
                    } catch (e: JSONException) {
                        Log.e("error", "Json parsing error: " + e.message)
                    }
                } else {
                    Log.e("error", "Couldn't get json from server.")
                }
            }.start()
        }
    }



    private fun makeServiceCall(strUrl: String): String? {
        var response: String? = null
        try {
            val url = URL(strUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            val inStream = BufferedReader(InputStreamReader(connection.inputStream))
            val sb = StringBuilder()
            var line: String?
            while (inStream.readLine().also { line = it } != null) {
                sb.append(line)
            }
            response = sb.toString()
            inStream.close()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }
}
