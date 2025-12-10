package com.monicadominguez.persistenciaagenda

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListActivity : AppCompatActivity() {

    class Contacte(var nom: String, var cognoms: String, var telefon: String, var email: String)
    var contactes: ArrayList<Contacte> = ArrayList<Contacte>()
    lateinit var adapter: ArrayAdapter<Contacte>

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
        val btnOk = findViewById<Button>(R.id.btnOk)

        btnOk.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        try {
            val fis = openFileInput("contactes.txt")
            val lines = fis.bufferedReader().readLines()
            fis.close()

            for (line in lines) {
                val parts = line.split(";")
                if(parts.size == 4) {
                    contactes.add(Contacte(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim()
                    ))
                }
            }
        } catch (e: Exception) {
            contactes.add(Contacte(
                "No hay contactos",
                "",
                "",
                "Clica Ok para volver a la página principal"
            ))
        }

        adapter = object: ArrayAdapter<Contacte>(this, R.layout.list_item,contactes) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView

                if (view == null) {
                    view = layoutInflater.inflate(R.layout.list_item, parent,false)
                }

                val contacte = getItem(position)

                if (contacte != null) {
                    view?.findViewById<TextView>(R.id.itemNom)?.text = "Nombre: ${contacte.nom}"
                    view?.findViewById<TextView>(R.id.itemCognoms)?.text = "Apellidos: ${contacte.cognoms}"
                    view?.findViewById<TextView>(R.id.itemEmail)?.text = "Email: ${contacte.email}"
                    view?.findViewById<TextView>(R.id.itemTf)?.text = "Teléfono: ${contacte.telefon}"
                }

                return view!!
            }
        }

        listContactes.adapter = adapter
    }
}