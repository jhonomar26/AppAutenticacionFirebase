package com.example.appautenticacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appautenticacion.R
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var textViewWelcome: TextView
    private lateinit var buttonLogout: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var buttonOpenMap: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        textViewWelcome = findViewById(R.id.textViewWelcome)
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonOpenMap = findViewById(R.id.buttonOpenMap)
        buttonOpenMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }


        // Verificar si el usuario está autenticado
        val user = mAuth.currentUser
        if (user == null) {
            // Si no hay usuario autenticado, redirigir a LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        textViewWelcome.text = "Bienvenido, ${user.email}!"

        buttonLogout.setOnClickListener {
            logoutUser()
        }


    }

    private fun logoutUser() {
        mAuth.signOut()
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
