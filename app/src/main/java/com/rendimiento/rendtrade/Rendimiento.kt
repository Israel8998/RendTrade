package com.rendimiento.rendtrade

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_rendimiento.*

class Rendimiento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rendimiento)

        //Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")

        //Mantener la sesión iniciada
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.apply()

        //Cerrar sesión
        btnCerrarSesion.setOnClickListener {
            //Borrar los datos almacenados para mantener sesión iniciada
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            showInicioSesion() //Dirigirse a la pantalla de inicio sesión
        }
    }

    //Dirigirse a la pantalla de inicio de sesión
    private fun showInicioSesion() {
        val inicioSesionIntent = Intent(this, LoginActivity::class.java)
        startActivity(inicioSesionIntent)
    }
}