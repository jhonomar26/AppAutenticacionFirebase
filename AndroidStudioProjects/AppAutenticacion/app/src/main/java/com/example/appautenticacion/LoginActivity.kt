package com.example.appautenticacion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException


class LoginActivity : AppCompatActivity() {
//Variables de instancia
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var textViewForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Autenticacion para manejar firebase
        mAuth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegister = findViewById(R.id.textViewRegister)
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword)

        buttonLogin.setOnClickListener {
            loginUser()
        }

        textViewRegister.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        textViewForgotPassword.setOnClickListener {
            finish()
            startActivity(Intent(this, forgetPassword::class.java))
        }
    }

    private fun loginUser() {
        //Se quitan los espacios
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        //Autenticacion por firebase
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                // Navegar a la siguiente actividad
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Error en el inicio de sesión: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("ERROR", "Error en el inicio de sesión: ${task.exception?.message}")
            }
        }
    }

}
