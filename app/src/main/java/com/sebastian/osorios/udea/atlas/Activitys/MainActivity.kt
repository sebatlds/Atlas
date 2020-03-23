package com.sebastian.osorios.udea.atlas.Activitys

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.sebastian.osorios.udea.atlas.DB.SesionRoom
import com.sebastian.osorios.udea.atlas.Interfaces.UserDAO
import com.sebastian.osorios.udea.atlas.Models.User.Usuario
import com.sebastian.osorios.udea.atlas.R
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val objetoIntent : Intent = intent
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById<NavigationView>(R.id.nav_view)
        val navController   = findNavController(R.id.nav_host_fragment)
        val view : View = LayoutInflater.from(this).inflate(R.layout.nav_header_main,null)
        navView.addHeaderView(view)
        val textViewName : TextView = view.findViewById(R.id.textViewNameNavHeader)
        val textViewEmail : TextView = view.findViewById(R.id.textViewEmailNavHeader)
        val imageView : CircleImageView = view.findViewById(R.id.imageViewCircle)
        var id = intent.getStringExtra("id")
        val userDAO : UserDAO = SesionRoom.database.UserDAO()
        val usuario : Usuario = userDAO.searchUserId(id.toInt())
        if(usuario != null){
            textViewEmail.text = usuario.email
            textViewName.text = usuario.name+" "+usuario.lastName
        }

        imageView.setImageResource(R.drawable.images)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_countries,
                R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onOptionsItemSelected(item : MenuItem) : Boolean{

        var id : Int = item.itemId

        if(id == R.id.action_close){
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
