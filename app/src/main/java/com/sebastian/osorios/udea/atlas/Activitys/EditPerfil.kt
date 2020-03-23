package com.sebastian.osorios.udea.atlas.Activitys

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.sebastian.osorios.udea.atlas.Interfaces.UserDAO
import com.sebastian.osorios.udea.atlas.Models.User.Usuario
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.DB.SesionRoom
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import com.sebastian.osorios.udea.atlas.Util.DatePickerFragment
import kotlinx.android.synthetic.main.activity_edit_perfil.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class EditPerfil : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil)
        var checkMenEdit : RadioButton = findViewById(R.id.edit_chel_men)
        var checkWomenEdit : RadioButton = findViewById(R.id.edit_chel_women)
        var nameEdit : EditText = findViewById(R.id.edit_name)
        var lastName : EditText = findViewById(R.id.edit_last_name)
        var emailEdit : EditText = findViewById(R.id.edit_email)
        var dateEdit : EditText = findViewById(R.id.edit_date)

        var id: String  = intent?.getStringExtra("id").toString()
        val userDAO : UserDAO = SesionRoom.database.UserDAO()
        val usuario : Usuario = userDAO.searchUserId(id.toInt())

        if(usuario != null){
            nameEdit.hint = usuario.name
            lastName.hint = usuario.lastName
            emailEdit.hint = usuario.email
            dateEdit.hint = usuario.date
            if("Hombre".equals(usuario.gender)){
                checkMenEdit.isChecked = true
                checkWomenEdit.isChecked = false
            }else{
                checkMenEdit.isChecked = false
                checkWomenEdit.isChecked = true
            }
        }else{
            val btnEdit : Button = findViewById(R.id.btn_save_edit)
            btnEdit.isEnabled= false
            val commonFunctions : CommonFunctions = CommonFunctions()
            Toast.makeText(this,commonFunctions.getErrorMessage("502",""), Toast.LENGTH_LONG ).show()
            val intento = Intent(this, LandingActivity ::class.java)
            startActivity(intento)
            finish()
        }

        btn_save_edit.setOnClickListener {
            var userDAO : UserDAO = SesionRoom.database.UserDAO()
            var gender : String
            var name : String
            var lastNameEdit : String
            var email : String
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

            if(emailEdit.text.toString().equals("")){
                email = usuario.email
            }else{
                email = emailEdit.text.toString()
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
            val user : Unit = userDAO.updateUser(
                Usuario(
                    usuario.id,
                    email,
                    name,
                    lastNameEdit,
                    usuario.password,
                    date,
                    gender
                )
            )
            Toast.makeText(this,"Actualización conrrecta",Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext ,MainActivity::class.java)
            intent.putExtra("id",id)
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

