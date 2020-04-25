package com.sebastian.osorios.udea.atlas.Activitys

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.facebook.AccessToken
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.sebastian.osorios.udea.atlas.Interfaces.UserDAO
import com.sebastian.osorios.udea.atlas.Models.User.Usuario
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.ReadDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef : DatabaseReference
    private lateinit var email : String
    var usuario : Usuario? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById<NavigationView>(R.id.nav_view)
        val navController   = findNavController(R.id.nav_host_fragment)
        val view : View = LayoutInflater.from(this).inflate(R.layout.nav_header_main,null)
        val textViewName : TextView = view.findViewById(R.id.textViewNameNavHeader)
        val textViewEmail : TextView = view.findViewById(R.id.textViewEmailNavHeader)
        val imageView : CircleImageView = view.findViewById(R.id.imageViewCircle)
        navView.addHeaderView(view)
       /* val authType = intent.getStringExtra("auth")
        if(authType.equals("email")){
            imageView.setImageResource(R.drawable.images)
            email = intent.getStringExtra("email")
            database = FirebaseDatabase.getInstance()
            myRef = database.getReference("usuarios")
            val postListener = object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(snapshot: DataSnapshot in dataSnapshot.children){
                        val map : DataSnapshot = snapshot
                        if(email.equals(map.child("email").value)){
                            textViewEmail.text = email
                            textViewName.text = map.child("name").value.toString() + " " + map.child("lastName").value.toString()
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            myRef.addValueEventListener(postListener)

        }else{
            val firebaseUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser

            Toast.makeText(this,"hizo login",Toast.LENGTH_LONG).show()
            if(firebaseUser != null){
                Toast.makeText(this,"llego a cargar datos",Toast.LENGTH_LONG).show()
                textViewName.text = firebaseUser.displayName.toString()
                textViewEmail.text  = firebaseUser.email.toString()
                Picasso.get().load(firebaseUser.photoUrl).into(imageView)
            }else{
                    goLanding()
                }


        }*/


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
        LoginManager.getInstance().logOut()
        FirebaseAuth.getInstance().signOut()
        val intento = Intent(this, LandingActivity ::class.java)
        startActivity(intento)
        finish()
    }

    private fun goLanding(){
        LoginManager.getInstance().logOut()
        FirebaseAuth.getInstance().signOut()
        val intento : Intent = Intent(applicationContext,LandingActivity::class.java)
        intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intento)
        finish()
    }

}
