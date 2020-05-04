package com.sebastian.osorios.udea.atlas.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.sebastian.osorios.udea.atlas.Activitys.EditPerfil
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.Constants
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class PerfilFragment : Fragment() {


    var linearLayoutGenero : LinearLayout? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef : DatabaseReference
    private lateinit var email : String
    private lateinit var id : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_perfil, container, false)
        linearLayoutGenero  = root.findViewById(R.id.linearLyaoutGenero)
        val intent = activity?.intent
        var iconEdit : ImageView?  = root.findViewById(R.id.edit_perfil)
        var textViewName : TextView = root.findViewById(R.id.user_name)
        var textViewLastName : TextView = root.findViewById(R.id.last_name)
        var textViewEmail : TextView = root.findViewById(R.id.email_perfil)
        var textViewDate : TextView = root.findViewById(R.id.perfil_date)
        var textViewGenero : TextView = root.findViewById(R.id.genero_perfil)
        var imageViewGenero : ImageView = root.findViewById(R.id.imageView_genero)
        var imagePerfil : CircleImageView = root.findViewById(R.id.imagePerfil)
        var url : String = "null"
        var auth = intent?.getStringExtra("auth")
        if(auth.equals("email")){
            email = intent!!.getStringExtra("email")
            database = FirebaseDatabase.getInstance()
            myRef = database.getReference("usuarios")
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(snapshot: DataSnapshot in dataSnapshot.children){
                        val map : DataSnapshot = snapshot
                        if(email.equals(map.child("email").value)){
                            if(map.child("image").value.toString().equals("null")){
                                imagePerfil.setImageResource(R.drawable.images)
                            }else{
                                url = map.child("image").value.toString()
                                Picasso.get().load(map.child("image").value.toString()).into(imagePerfil)
                            }
                            textViewEmail.text = email
                            textViewName.text = map.child("name").value.toString()
                            textViewLastName.text = map.child("lastName").value.toString()
                            textViewDate.text = map.child("date").value.toString()
                            textViewGenero.text = map.child("gender").value.toString()
                            id = map.child("id").value.toString()
                            if(map.child("gender").value.toString().equals("Hombre")){
                                imageViewGenero.setImageResource(R.drawable.drawable_grupo1_icon_masculino)
                            }else{
                                imageViewGenero.setImageResource(R.drawable.drawable_grupo1_icon_femenino)
                            }
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            myRef.addValueEventListener(postListener)


        }else{
            var firebaseUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
            if(firebaseUser != null){
                val indexs = firebaseUser.displayName.toString().split(" ")
                textViewName.text = indexs.get(0)
                textViewLastName.text = indexs.get(1)
                textViewEmail.text  = firebaseUser.email.toString()
                Picasso.get().load(firebaseUser.photoUrl).into(imagePerfil)
            }
            iconEdit!!.isVisible = false
        }

        iconEdit?.setOnClickListener(){
            val constants = Constants()
            val intent = Intent(this.context , EditPerfil::class.java)
            intent.putExtra("email",email)
            intent.putExtra("name",textViewName.text)
            intent.putExtra("lastName",textViewLastName.text)
            intent.putExtra("gender",textViewGenero.text)
            intent.putExtra("date",textViewDate.text)
            intent.putExtra("id",id)
            intent.putExtra("image", url)
            startActivity(intent)
        }




        return root
    }




}