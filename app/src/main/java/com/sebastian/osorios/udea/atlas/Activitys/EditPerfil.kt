package com.sebastian.osorios.udea.atlas.Activitys

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.DatePickerFragment
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

        nameEdit.hint = intent.getStringExtra("name_perfil").toString()
        lastName.hint = intent.getStringExtra("last_name_perfil").toString()
        emailEdit.hint = intent.getStringExtra("email_perfil")
        dateEdit.hint = intent.getStringExtra("date_perfil")

        if("Hombre".equals(intent.getStringExtra("genero_perfil"))){
            checkMenEdit.isChecked = true
            checkWomenEdit.isChecked = false
        }else{
            checkMenEdit.isChecked = false
            checkWomenEdit.isChecked = true
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
