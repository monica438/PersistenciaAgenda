package com.monicadominguez.persistenciaagenda

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listContactes = findViewById<ListView>(R.id.listContactes)

        val contactes = ArrayList<String>()

        try {
            val fis = openFileInput("contactes.txt")
            val text = fis.bufferedReader().readLines()
            fis.close()

            contactes.addAll(text)
        } catch (e: Exception) {
            contactes.add("No hi ha contactes!")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactes)
        listContactes.adapter = adapter
    }
}