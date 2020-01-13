package com.sebastian.osorios.udea.atlas

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnLogIn : Button = findViewById(R.id.login)
        val btnRegister : Button = findViewById(R.id.register)
        val btnContinue : Button = findViewById(R.id.continu)
        val btnCancel : Button = findViewById(R.id.cancel)
        val linearLayout : LinearLayout = findViewById(R.id.linearLyaout)
        val linearLayoutButtons : LinearLayout = findViewById(R.id.linearLyaoutButtons)
        val linearLayoutButtonLogin : LinearLayout = findViewById(R.id.buttonLogin)
        var editTextUserLogin : EditText = findViewById(R.id.userLogin)
        var editTextPassLogin : EditText = findViewById(R.id.passLogin)

        btnLogIn.setOnClickListener {
            linearLayout.isVisible= true
            linearLayoutButtonLogin.isVisible  = true
            linearLayoutButtons.isVisible = false
        }

        btnRegister.setOnClickListener {
            val intento = Intent(this, RegisterInAtlas ::class.java)
            startActivity(intento)
        }

        btnContinue.setOnClickListener {
            if(editTextUserLogin.text.toString().equals("seosori") && editTextPassLogin.text.toString().equals("vexaxe73")){
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Error")
                alert.setMessage("Inicio de sesion correcto")
                alert.setPositiveButton(
                    "Confirmar",null)
                alert.show()
            }else{
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Error")
                alert.setMessage("Datos incorrectos!!!")
                alert.setPositiveButton(
                    "Confirmar",null)
                alert.show()
            }
        }

        btnCancel.setOnClickListener {
            linearLayout.isVisible= false
            linearLayoutButtons.isVisible = true
            linearLayoutButtonLogin.isVisible = false
            editTextPassLogin.setText("")
            editTextUserLogin.setText("")

        }



    }
}
