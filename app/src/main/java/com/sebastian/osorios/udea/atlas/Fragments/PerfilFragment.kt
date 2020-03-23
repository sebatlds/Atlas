package com.sebastian.osorios.udea.atlas.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sebastian.osorios.udea.atlas.Activitys.EditPerfil
import com.sebastian.osorios.udea.atlas.Activitys.LandingActivity
import com.sebastian.osorios.udea.atlas.Activitys.MainActivity
import com.sebastian.osorios.udea.atlas.Interfaces.UserDAO
import com.sebastian.osorios.udea.atlas.Models.User.Usuario
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.DB.SesionRoom
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import com.sebastian.osorios.udea.atlas.Util.Constants


class PerfilFragment : Fragment() {


    var linearLayoutGenero : LinearLayout? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_perfil, container, false)
        linearLayoutGenero  = root.findViewById(R.id.linearLyaoutGenero)
        val intent = activity!!.intent
        var iconEdit : ImageView?  = root.findViewById(R.id.edit_perfil)
        var textViewName : TextView = root.findViewById(R.id.user_name)
        var textViewLastName : TextView = root.findViewById(R.id.last_name)
        var textViewEmail : TextView = root.findViewById(R.id.email_perfil)
        var textViewDate : TextView = root.findViewById(R.id.perfil_date)
        var textViewGenero : TextView = root.findViewById(R.id.genero_perfil)
        var imageViewGenero : ImageView = root.findViewById(R.id.imageView_genero)
        var id  = intent.getStringExtra("id")
        val userDAO : UserDAO = SesionRoom.database.UserDAO()
        val user : Usuario = userDAO.searchUserId(id.toInt())
        var gender : String
        if(user != null){
            var name = user.name
            var lastName = user.lastName
            var email = user.email
            var date = user.date
            if(user.gender.equals("Hombre")){
                imageViewGenero.setImageResource(R.drawable.macho)
                gender = "Hombre"
            }else{
                imageViewGenero.setImageResource(R.drawable.femenino)
                gender = "Mujer"
            }
            textViewName.text = name
            textViewLastName.text = lastName
            textViewEmail.text = email
            textViewDate.text = date
            textViewGenero.text = gender
        }else{
            val commonFunctions : CommonFunctions = CommonFunctions()
            Toast.makeText(context,commonFunctions.getErrorMessage("502",""),Toast.LENGTH_LONG)
            val intento = Intent(context, LandingActivity ::class.java)
            startActivity(intento)
        }

        iconEdit?.setOnClickListener(){
            val constants = Constants()
            val intent = Intent(this.context , EditPerfil::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
            startActivityForResult(intent,constants.REQUEST_CODE)
        }




        return root
    }




}