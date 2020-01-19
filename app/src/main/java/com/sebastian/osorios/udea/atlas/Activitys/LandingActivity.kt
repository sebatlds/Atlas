package com.sebastian.osorios.udea.atlas.Activitys

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sebastian.osorios.udea.atlas.Interfaces.ApiServices
import com.sebastian.osorios.udea.atlas.Models.BaseModel
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.sebastian.osorios.udea.atlas.R


class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        val btnLogIn : Button = findViewById(R.id.login)
        val btnRegister : Button = findViewById(R.id.register)
        val btnContinue : Button = findViewById(R.id.continu)
        val btnCancel : Button = findViewById(R.id.cancel)
        val linearLayout : LinearLayout = findViewById(R.id.linearLyaout)
        val linearLayoutButtons : LinearLayout = findViewById(R.id.linearLyaoutButtons)
        val linearLayoutButtonLogin : LinearLayout = findViewById(R.id.buttonLogin)
        var editTextUserLogin : EditText = findViewById(R.id.userLogin)
        var editTextPassLogin : EditText = findViewById(R.id.passLogin)
        var seePass : ImageView = findViewById(R.id.seePassword)
        val alert = AlertDialog.Builder(this)
        var check : Boolean = true
        btnLogIn.setOnClickListener {
            linearLayout.isVisible= true
            linearLayoutButtonLogin.isVisible  = true
            linearLayoutButtons.isVisible = false
        }

        seePass.setOnClickListener {

                editTextPassLogin.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                check=true

        }

        btnRegister.setOnClickListener {
            val intento = Intent(this, RegisterInAtlas::class.java)
            startActivity(intento)
        }

        btnContinue.setOnClickListener {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://gorest.co.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ApiServices::class.java)

            service.getUser(editTextUserLogin.text.toString()).enqueue(object: Callback<BaseModel> {
                override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {

                        if(response.body()?.result?.isEmpty()!!){
                            val commonFunctions = CommonFunctions()
                            alert.setTitle("Error")
                            alert.setMessage(commonFunctions.getErrorMessage("403",
                                ""))
                            alert.setPositiveButton(
                                "Confirmar",null)
                            alert.show()
                        }else{
                            validatePassword(editTextPassLogin.text.toString(), response.body()!!.result.get(0).password)
                        }



                }

                override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                    val commonFunctions = CommonFunctions()
                    alert.setTitle("Error")
                    alert.setMessage(commonFunctions.getErrorMessage("501",""))
                    alert.setPositiveButton(
                        "Confirmar",null)
                    alert.show()
                }
            })
        }

        btnCancel.setOnClickListener {
            linearLayout.isVisible= false
            linearLayoutButtons.isVisible = true
            linearLayoutButtonLogin.isVisible = false
            editTextPassLogin.setText("")
            editTextUserLogin.setText("")

        }
    }

    fun validatePassword(passInput : String, passDb :String){
        if(passInput.equals(passDb)){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Error")
            alert.setMessage("Contraseña incorrecta!!")
            alert.setPositiveButton(
                "Confirmar",null)
            alert.show()
        }
    }
}
