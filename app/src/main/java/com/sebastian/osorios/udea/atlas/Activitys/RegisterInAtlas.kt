package com.sebastian.osorios.udea.atlas.Activitys

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sebastian.osorios.udea.atlas.Models.User.Usuario
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.CheckInternetConexion
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import com.sebastian.osorios.udea.atlas.Util.Constants
import com.sebastian.osorios.udea.atlas.Util.DatePickerFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RegisterInAtlas : AppCompatActivity(){


    val constants = Constants()
    lateinit var auth : FirebaseAuth
    lateinit var progressDialog : ProgressDialog
    lateinit var alert : AlertDialog.Builder
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_in_atlas)
        val buttonSaveRegister : Button = findViewById(R.id.saveRegister)
        auth = FirebaseAuth.getInstance()
        var editTextNameRegister : EditText = findViewById(R.id.name)
        var editTextLastNameRegister : EditText = findViewById(R.id.last_name)
        var editTextEmailRegister : EditText = findViewById(R.id.email)
        var editTextPassRegister : EditText = findViewById(R.id.password)
        var editTextPass2 : EditText = findViewById(R.id.password2)
        var editTextDateRegister : EditText = findViewById(R.id.EditTextdate)
        var radioButtonMenRegister : RadioButton = findViewById(R.id.checkMen)
        var radioButtonWomenRegister : RadioButton = findViewById(R.id.checkWomen)
        var imageView : ImageView = findViewById(R.id.imageView)
        alert = AlertDialog.Builder(this)
        val checkInternetConexion = CheckInternetConexion()


        imageView.setOnClickListener {
            toastAlertTooltip()
        }




        editTextDateRegister.setOnClickListener(){
            val datePickerFragment : DatePickerFragment  = DatePickerFragment()
            var calendar : Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    editTextDateRegister.setText(year.toString() + "/" + (month+1).toString() + "/" + dayOfMonth.toString())
                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formatted = current.format(formatter)
            datePicker.datePicker.maxDate= datePickerFragment.convertDateToMillis(formatted.toString())
            datePicker.show()
        }



        radioButtonMenRegister.setOnClickListener{
            radioButtonMenRegister.isChecked=true
            radioButtonWomenRegister.isChecked=false
        }

        radioButtonWomenRegister.setOnClickListener{
            radioButtonMenRegister.isChecked=false
            radioButtonWomenRegister.isChecked=true
        }

        buttonSaveRegister.setOnClickListener {
            progressDialog  = ProgressDialog(this)
            progressDialog.show()
            progressDialog.setContentView(R.layout.progress_dialog)
            progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            if(checkInternetConexion.isConnectedToThisServer(constants.GOOGLE_HOST)){
                buttonSaveRegister.isEnabled=false
                if (editTextEmailRegister.text.toString().equals("") || editTextPassRegister.text.toString().equals("") ||
                    editTextNameRegister.text.toString().equals("") ||
                    editTextLastNameRegister.text.toString().equals("") || editTextDateRegister.text.toString().equals("") ||
                    editTextPass2.text.toString().equals("")
                ) {
                    showAlert("409")
                    buttonSaveRegister.isEnabled= true
                } else if (!(editTextPassRegister.text.length >= 6)) {
                    showAlert("410")
                    buttonSaveRegister.isEnabled= true
                }else if(!(editTextPassRegister.text.toString().equals(editTextPass2.text.toString()))){
                    showAlert("411")
                    buttonSaveRegister.isEnabled= true
                }
                else {
                    var gender: String;
                    if (radioButtonMenRegister.isChecked) {
                        gender = "Hombre"
                    } else {
                        gender = "Mujer"
                    }
                    createUser(editTextEmailRegister.text.toString(),editTextPassRegister.text.toString(),gender)
                }
            }else{
                showAlert("402")
            }

        }

    }

    fun createUser(email : String , password : String,gender : String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val database = FirebaseDatabase.getInstance()
                    var myRef = database.getReference("usuarios")
                    var id = myRef.push().key
                    val usuario = Usuario(
                        id!!,
                        email,
                        findViewById<EditText>(R.id.name).text.toString(),
                        findViewById<EditText>(R.id.last_name).text.toString(),
                        findViewById<EditText>(R.id.EditTextdate).text.toString(),
                        gender,
                        "null"
                    )
                    myRef.child(id).setValue(usuario)
                    toastAlertRegistration()
                    backActivity()
                }else{

                    if(task.exception!!.message.equals("The email address is already in use by another account.")){
                        showAlert("408")
                    }else{
                        progressDialog.dismiss()
                    }
                    findViewById<Button>(R.id.saveRegister).isEnabled=true
                }
            }
    }

    fun backActivity() {
        val intento = Intent(this, LandingActivity::class.java)
        startActivity(intento)
        progressDialog.dismiss()
        finish()
    }

    fun toastAlertTooltip(){
        Toast.makeText(this,"La contraseña debe tener por lo menos 6 dígitos.",Toast.LENGTH_LONG ).show()
    }

    fun toastAlertRegistration(){
        Toast.makeText(this,"Registro exitoso.",Toast.LENGTH_LONG ).show()
    }

    private fun showAlert(code : String){
        progressDialog.dismiss()
        val commonFunctions = CommonFunctions()
        alert.setTitle("Error")
        alert.setMessage(commonFunctions.getErrorMessage(code))
        alert.setPositiveButton(
            "Confirmar", null
        )
        alert.show()
    }

}

