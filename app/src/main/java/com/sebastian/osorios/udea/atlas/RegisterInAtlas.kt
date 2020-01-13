package com.sebastian.osorios.udea.atlas

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class RegisterInAtlas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_in_atlas)
        val buttonSaveRegister : Button = findViewById(R.id.saveRegister)
        var editTextUserRegister : EditText = findViewById(R.id.userRegis)
        var editTextPassRegister : EditText = findViewById(R.id.passRegis)
        var editTextEmailRegister : EditText = findViewById(R.id.emailRegis)

        buttonSaveRegister.setOnClickListener {
            var a =
            if(editTextEmailRegister.text.toString().equals("") || editTextPassRegister.text.toString().equals("") || editTextUserRegister.text.toString() .equals("")){
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Error")
                alert.setMessage("Faltan campos por llenar.")
                alert.setPositiveButton(
                    "Confirmar",null)
                alert.show()
            }else{
                //todo verificar que el usuario  y email sea unico.
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Error")
                alert.setMessage(editTextUserRegister.text.toString()+" "+editTextPassRegister.text.toString())
                alert.setPositiveButton(
                    "Confirmar",null)
                alert.show()
            }
        }

    }
}
