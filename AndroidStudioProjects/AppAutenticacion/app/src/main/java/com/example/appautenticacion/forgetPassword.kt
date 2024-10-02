package com.example.appautenticacion

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class forgetPassword : AppCompatActivity() {
    private lateinit var textViewForgotPassword: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var editTextEmail: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forget_password)
        mAuth = FirebaseAuth.getInstance()
        textViewForgotPassword = findViewById(R.id.buttonSendEmail)
        editTextEmail = findViewById(R.id.editTextEmail)

        textViewForgotPassword.setOnClickListener {
            resetPassword()
        }
    }

    // Método para manejar el restablecimiento de la contraseña
    private fun resetPassword() {
        val email = editTextEmail.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT)
                .show()
            return
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Correo de restablecimiento enviado", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this, LoginActivity::class.java))

            } else {
                // Manejo de errores específicos
                val errorMessage = when (task.exception) {
                    is FirebaseAuthInvalidUserException -> "El correo electrónico no está registrado"
                    else -> "Error al enviar el correo de restablecimiento: ${task.exception?.message}"
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("ERROR", errorMessage)
            }
        }
    }
}