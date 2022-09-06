package com.rendimiento.rendtrade

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setup()
        session()
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        if(email != null){
            showRendimiento(email)
        }
    }

    private fun setup() {
        //Función del botón para validar cuenta e iniciar sesión
        btnIngresar.setOnClickListener {
            if (txtCorreo.text.isNotEmpty()) { //Comprobar que no esté vacío el texto del correo
                if(txtContraseña.text.isNotEmpty()) { //Comprobar que no esté vacío el texto de la contraseña
                    if (txtContraseña.length() >= 6) { //Comprobar que tenga el mínimo de caracteres en contraseña
                        FirebaseAuth.getInstance().signInWithEmailAndPassword( //Función para validación de la BDD
                            txtCorreo.text.toString(), txtContraseña.text.toString() //Envío de valores
                        ).addOnCompleteListener {
                            if (it.isSuccessful) { //Condición en caso de tener éxito
                                showRendimiento(it.result?.user?.email?:"")
                            } else { //Condición en caso de estar algo mal
                                showAlert()
                            }
                        }
                    } else { //Mensaje error para la cantidad mínima de caracteres en contraseña
                        txtContraseña.setError("La contraseña debe tener mínimo 6 caracteres")
                    }
                } else { //Mensaje error si está vacío el campo de la contraseña
                    txtContraseña.setError("La contraseña no puede estar vacía")
                }
            } else { //Mensaje error si está vacío el campo del correo
                txtCorreo.setError("El correo no puede estar vacío")
            }
        }
    }

    //Envío a otra pantalla en caso de tener éxito al iniciar sesión
    private fun showRendimiento(email: String) {
        val RendimientoIntent = Intent(this, Rendimiento::class.java).apply{
            putExtra("email", email)
        }
        startActivity(RendimientoIntent)
    }

    //Creación de poop up en caso de existir error al iniciar sesión
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error") //Título del poop up del error
        builder.setMessage("Correo o contraseña incorrectos") //Descripción del error
        builder.setPositiveButton("Aceptar", null) //botón para cerra el poop up
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}