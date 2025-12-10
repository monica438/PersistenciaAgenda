package com.monicadominguez.persistenciaagenda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNom = findViewById<EditText>(R.id.itemNom)
        val etCognoms = findViewById<EditText>(R.id.itemCognoms)
        val etTelefon = findViewById<EditText>(R.id.telefon)
        val etEmail = findViewById<EditText>(R.id.email)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnLista = findViewById<Button>(R.id.btnVerLista)

        btnLista.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        btnGuardar.setOnClickListener {
            val nom = etNom.text.toString()
            val cognoms = etCognoms.text.toString()
            val telefon = etTelefon.text.toString()
            val email = etEmail.text.toString()

            val linia = "$nom;$cognoms;$telefon;$email\n"

            try {
                val fos = openFileOutput("contactes.txt",MODE_APPEND)
                fos.write(linia.toByteArray())
                fos.close()
                Toast.makeText(this, "Contacte guardat correctament!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error en guardar: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

    }
}