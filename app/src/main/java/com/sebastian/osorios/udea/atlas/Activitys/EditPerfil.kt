package com.sebastian.osorios.udea.atlas.Activitys

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.database.FirebaseDatabase
import com.sebastian.osorios.udea.atlas.Models.User.Usuario
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.DatePickerFragment
import kotlinx.android.synthetic.main.activity_edit_perfil.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class EditPerfil : AppCompatActivity() {

    lateinit var email : String
    lateinit var usuario : Usuario
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil)
        email = intent?.getStringExtra("email").toString()

        var checkMenEdit : RadioButton = findViewById(R.id.edit_chel_men)
        var checkWomenEdit : RadioButton = findViewById(R.id.edit_chel_women)
        var nameEdit : EditText = findViewById(R.id.edit_name)
        var lastName : EditText = findViewById(R.id.edit_last_name)
        var dateEdit : EditText = findViewById(R.id.edit_date)
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef = database.getReference("usuarios")
        usuario = Usuario(
            intent?.getStringExtra("id").toString(),
            intent?.getStringExtra("email").toString(),
            intent?.getStringExtra("name").toString(),
            intent?.getStringExtra("lastName").toString(),
            intent?.getStringExtra("date").toString(),
            intent?.getStringExtra("gender").toString()
        )
        nameEdit.hint = usuario.name
        lastName.hint = usuario.lastName
        dateEdit.hint = usuario.date
        if("Hombre".equals(usuario.gender)){
            checkMenEdit.isChecked = true
            checkWomenEdit.isChecked = false
        }else{
            checkMenEdit.isChecked = false
            checkWomenEdit.isChecked = true
        }

        btn_save_edit.setOnClickListener {
            var gender : String
            var name : String
            var lastNameEdit : String
            var date : String
            if(nameEdit.text.toString().equals("")){
                name = usuario.name
            }else{
                name = nameEdit.text.toString()
            }
            if(lastName.text.toString().equals("")){
                lastNameEdit = usuario.lastName
            }else{
                lastNameEdit = lastName.text.toString()
            }
            if(dateEdit.text.toString().equals("")){
                date = usuario.date
            }else{
                date = dateEdit.text.toString()
            }
            if(checkMenEdit.isChecked){
                gender = "Hombre"
            }else{
                gender = "Mujer"
            }
            val childUpdates = HashMap<String,Any>()
            childUpdates["email"]= email
            childUpdates["name"]=name
            childUpdates["last_name"]=lastNameEdit
            childUpdates["date"]=date
            childUpdates["gender"]=gender
            myRef.child(usuario.id).updateChildren(childUpdates)
            Toast.makeText(this,"Actualización conrrecta",Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext ,MainActivity::class.java)
            intent.putExtra("email",email)
            startActivity(intent)
            finish()
        }


        dateEdit.setOnClickListener(){
            val datePickerFragment : DatePickerFragment = DatePickerFragment()
            var calendar : Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    dateEdit.setText(year.toString() + "/" + (month+1).toString() + "/" + dayOfMonth.toString())
                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formatted = current.format(formatter)
            datePicker.datePicker.maxDate= datePickerFragment.convertDateToMillis(formatted.toString())
            datePicker.show()
        }


    }


}

