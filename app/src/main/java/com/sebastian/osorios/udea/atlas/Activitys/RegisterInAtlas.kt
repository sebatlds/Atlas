package com.sebastian.osorios.udea.atlas.Activitys

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sebastian.osorios.udea.atlas.Interfaces.ApiServices
import com.sebastian.osorios.udea.atlas.Models.User.BaseModel
import com.sebastian.osorios.udea.atlas.Models.User.UserPost
import com.sebastian.osorios.udea.atlas.Util.CheckInternetConexion
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import com.sebastian.osorios.udea.atlas.Util.Constants
import com.sebastian.osorios.udea.atlas.Util.DatePickerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.sebastian.osorios.udea.atlas.R
import java.util.Calendar
import android.app.DatePickerDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class RegisterInAtlas : AppCompatActivity(){


    val constants = Constants()
    val commonFunctions = CommonFunctions()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_in_atlas)
        val buttonSaveRegister : Button = findViewById(R.id.saveRegister)
        var editTextNameRegister : EditText = findViewById(R.id.name)
        var editTextLastNameRegister : EditText = findViewById(R.id.last_name)
        var editTextEmailRegister : EditText = findViewById(R.id.email)
        var editTextPassRegister : EditText = findViewById(R.id.password)
        var editTextPass2 : EditText = findViewById(R.id.password2)
        var editTextDateRegister : EditText = findViewById(R.id.EditTextdate)
        var radioButtonMenRegister : RadioButton = findViewById(R.id.checkMen)
        var radioButtonWomenRegister : RadioButton = findViewById(R.id.checkWomen)
        var imageView : ImageView = findViewById(R.id.imageView)
        val alert = AlertDialog.Builder(this)
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
            if (checkInternetConexion.isConnectedToThisServer(constants.GOOGLE_HOST)){
                buttonSaveRegister.isEnabled=false
                if (editTextEmailRegister.text.toString().equals("") || editTextPassRegister.text.toString().equals("") ||
                    editTextNameRegister.text.toString().equals("") ||
                    editTextLastNameRegister.text.toString().equals("") || editTextDateRegister.text.toString().equals("") ||
                            editTextPass2.text.toString().equals("")
                ) {
                    val alert = AlertDialog.Builder(this)
                    alert.setTitle("Error")
                    alert.setMessage("Faltan campos por llenar.")
                    alert.setPositiveButton(
                        "Confirmar", null
                    )
                    alert.show()
                    buttonSaveRegister.isEnabled= true
                } else if (!(editTextPassRegister.text.length >= 6)) {
                    alert.setTitle("Error")
                    alert.setMessage("La contraseña no cumple con el tamaño solicitado.")
                    alert.setPositiveButton(
                        "Confirmar", null
                    )
                    alert.show()
                    buttonSaveRegister.isEnabled= true
                }else if(!(editTextPassRegister.text.toString().equals(editTextPass2.text.toString()))){
                    alert.setTitle("Error")
                    alert.setMessage("Las contraseñas no coinciden.")
                    alert.setPositiveButton(
                        "Confirmar", null
                    )
                    alert.show()
                    buttonSaveRegister.isEnabled= true
                }
                else {
                    /**
                     * Se arma el json
                     */
                    var gender: String;
                    if (radioButtonMenRegister.isChecked) {
                        gender = "male"
                    } else {
                        gender = "female"
                    }
                    val user =
                        UserPost(
                            editTextNameRegister.text.toString(),
                            editTextLastNameRegister.text.toString(),
                            gender,
                            editTextDateRegister.text.toString().replace("/", "-"),
                            editTextEmailRegister.text.toString(),
                            editTextPassRegister.text.toString(),
                            "active"
                        )
                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://gorest.co.in")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val service = retrofit.create(ApiServices::class.java)

                    service.createUser(user).enqueue(object : Callback<BaseModel> {
                        override fun onResponse(
                            call: Call<BaseModel>,
                            response: Response<BaseModel>
                        ) {
                            if (response.body()?._meta?.code!!.toInt() >= 300) {
                                buttonSaveRegister.isEnabled=true
                                alert.setTitle("Error")
                                alert.setMessage(
                                    commonFunctions.getErrorMessage(response.body()?._meta?.code.toString(),
                                        response.body()!!.result.get(0).message))
                                alert.setPositiveButton(
                                    "Confirmar", null
                                )
                                alert.show()
                            } else {
                                toastAlertRegistration()
                                backActivity()
                            }
                        }

                        override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                            toastAlertRegistration()
                            backActivity()
                        }
                    })

                }
            }else{
                val commonFunctions = CommonFunctions()
                alert.setTitle(constants.ERROR_TITLE)
                alert.setMessage(commonFunctions.getErrorMessage("402", ""))
                alert.setPositiveButton("Confirmar", null)
                alert.show()
            }
        }

    }




    fun backActivity() {
        val intento = Intent(this, LandingActivity::class.java)
        startActivity(intento)
        finish()
    }

    fun toastAlertTooltip(){
        Toast.makeText(this,"La contraseña debe tener por lo menos 6 dígitos.",Toast.LENGTH_LONG ).show()
    }

    fun toastAlertRegistration(){
        Toast.makeText(this,"Registro exitoso.",Toast.LENGTH_LONG ).show()
    }


}

