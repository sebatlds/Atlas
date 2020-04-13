package com.sebastian.osorios.udea.atlas.Util

import android.content.Context
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sebastian.osorios.udea.atlas.Models.User.Usuario

class ReadDatabase {

    fun getUser(email : String,context: Context): Usuario?{
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef = database.getReference("usuarios")
        var usuario : Usuario? = null
        val postListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot: DataSnapshot in dataSnapshot.children){
                    val map : DataSnapshot = snapshot
                    if(email.equals(map.child("email").value)){
                        usuario?.name = map.child("name").value.toString()
                        usuario?.lastName = map.child("lastName").value.toString()
                        usuario?.date = map.child("date").value.toString()
                        usuario?.gender = map.child("gender").value.toString()
                        usuario?.email = map.child("email").value.toString()
                        usuario?.id = map.child("id").value.toString()
                    }
                }
            }
        }
        myRef.addValueEventListener(postListener)
        return usuario
    }
}