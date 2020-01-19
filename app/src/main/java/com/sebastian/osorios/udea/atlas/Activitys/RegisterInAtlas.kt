package com.sebastian.osorios.udea.atlas.Activitys

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sebastian.osorios.udea.atlas.Interfaces.ApiServices
import com.sebastian.osorios.udea.atlas.Models.BaseModel
import com.sebastian.osorios.udea.atlas.R
import java.util.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.app.AlertDialog
import android.widget.*
import com.sebastian.osorios.udea.atlas.Models.UserPost
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions


class RegisterInAtlas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_in_atlas)
        val buttonSaveRegister : Button = findViewById(R.id.saveRegister)
        var editTextNameRegister : EditText = findViewById(R.id.name)
        var editTextLastNameRegister : EditText = findViewById(R.id.last_name)
        var editTextEmailRegister : EditText = findViewById(R.id.email)
        var editTextPassRegister : EditText = findViewById(R.id.password)
        var editTextDateRegister : EditText = findViewById(R.id.EditTextdate)
        var radioButtonMenRegister : RadioButton = findViewById(R.id.checkMen)
        var radioButtonWomenRegister : RadioButton = findViewById(R.id.checkWomen)
        var imageView : ImageView = findViewById(R.id.imageView)
        val alert = AlertDialog.Builder(this)

        //todo fechas anteriores


        imageView.setOnClickListener {
            toastAlertTooltip()
        }


        editTextDateRegister.setOnClickListener(){
            var calendar : Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    editTextDateRegister.setText(year.toString() + "/" + (month+1).toString() + "/" + dayOfMonth.toString())
                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.datePicker.maxDate
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

            if(editTextEmailRegister.text.toString().equals("") || editTextPassRegister.text.toString().equals("") || editTextNameRegister.text.toString() .equals("") ||
                editTextLastNameRegister.text.toString().equals("") || editTextDateRegister.text.toString().equals("")){
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Error")
                alert.setMessage("Faltan campos por llenar.")
                alert.setPositiveButton(
                    "Confirmar",null)
                alert.show()
            }else if(!(editTextPassRegister.text.length>=6)){
                alert.setTitle("Error")
                alert.setMessage("La contraseña no cumple con el tamaño solicitado.")
                alert.setPositiveButton(
                    "Confirmar",null)
                alert.show()
            } else{
               //armo json y llamo el servicio de registro
                var gender : String;
                if(radioButtonMenRegister.isChecked){
                    gender = "male"
                }else{
                    gender = "female"
                }
                val user = UserPost(
                    editTextNameRegister.text.toString(),
                    editTextLastNameRegister.text.toString(),
                    gender,
                    editTextDateRegister.text.toString().replace("/","-"),
                    editTextEmailRegister.text.toString(),
                    editTextPassRegister.text.toString(),
                    "active"
                )
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gorest.co.in")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(ApiServices::class.java)

                service.createUser(user).enqueue(object: Callback<BaseModel>{
                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                        if(response.body()?._meta?.code!!.toInt() >=300){
                            val commonFunctions = CommonFunctions()
                            alert.setTitle("Error")
                            alert.setMessage(commonFunctions.getErrorMessage(response.body()?._meta?.code.toString(),
                                response.body()!!.result.get(0).message.toString()))
                            alert.setPositiveButton(
                                "Confirmar",null)
                            alert.show()
                        }else{
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
        }

    }

    fun backActivity() {
        val intento = Intent(this, LandingActivity::class.java)
        startActivity(intento)
    }

    fun toastAlertTooltip(){
        Toast.makeText(this,"La contraseña debe tener por lo menos 6 dígitos.",Toast.LENGTH_LONG ).show()
    }

    fun toastAlertRegistration(){
        Toast.makeText(this,"Registro exitoso.",Toast.LENGTH_LONG ).show()
    }
}

