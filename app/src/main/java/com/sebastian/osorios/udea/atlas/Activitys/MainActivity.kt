package com.sebastian.osorios.udea.atlas.Activitys

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.sebastian.osorios.udea.atlas.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(applicationContext,intent.getStringExtra("email_landing"),Toast.LENGTH_LONG).show()
        var textView : TextView = findViewById(R.id.textViewEmail)
        textView.text = intent.getStringExtra("email_landing")

    }

    /**
     * Metodo para mostrar menu
     */
    override fun onCreateOptionsMenu(menu : Menu): Boolean{

         menuInflater.inflate(R.menu.overflow, menu )
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean{

        var id : Int = item.itemId

        if(id == R.id.closeSession){
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Cerrar Sesion")
            alert.setMessage("¿Esta seguro que desea cerrar sesion?")
            alert.setPositiveButton("Aceptar",{ dialogo1, id -> backToActivityLanding() })
            alert.setNegativeButton("Cancelar",null)
            alert.show()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun backToActivityLanding(){
        val intento = Intent(this, LandingActivity ::class.java)
        startActivity(intento)
        finish()
    }

}
