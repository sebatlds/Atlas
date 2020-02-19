package com.sebastian.osorios.udea.atlas.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sebastian.osorios.udea.atlas.Activitys.EditPerfil
import com.sebastian.osorios.udea.atlas.R


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
        var name = intent.getStringExtra("name")
        var lastName = intent.getStringExtra("lastName")
        var email = intent.getStringExtra("email")
        var date = intent.getStringExtra("date")
        var gender : String
        if(intent.getStringExtra("gender").equals("male")){
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


        iconEdit?.setOnClickListener(){
            val intent = Intent(this.context , EditPerfil::class.java)
            intent.putExtra("email_perfil",email)
            intent.putExtra("name_perfil",name)
            intent.putExtra("last_name_perfil",lastName)
            intent.putExtra("date_perfil",date)
            intent.putExtra("genero_perfil",gender)
            startActivity(intent)
        }




        return root
    }




}