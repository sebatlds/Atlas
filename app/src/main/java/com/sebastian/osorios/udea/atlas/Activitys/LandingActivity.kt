package com.sebastian.osorios.udea.atlas.Activitys

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sebastian.osorios.udea.atlas.Interfaces.ApiServices
import com.sebastian.osorios.udea.atlas.Models.User.BaseModel
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.CheckInternetConexion
import com.sebastian.osorios.udea.atlas.Util.Constants
import kotlinx.android.synthetic.main.activity_landing.*
import android.text.method.*


class LandingActivity : AppCompatActivity() {


    val constants = Constants()
    val commonFunctions = CommonFunctions()


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
        var noSeePass : ImageView = findViewById(R.id.NoseePassword)
        val alert = AlertDialog.Builder(this)


        btnLogIn.setOnClickListener {
            linearLayout.isVisible= true
            linearLayoutButtonLogin.isVisible  = true
            linearLayoutButtons.isVisible = false
        }

        btnRegister.setOnClickListener {
            val intento = Intent(this, RegisterInAtlas::class.java)
            startActivity(intento)
        }


        btnContinue.setOnClickListener {
            val checkInternetConexion = CheckInternetConexion()
            btnContinue.isEnabled = false
            if (checkInternetConexion.isConnectedToThisServer(constants.GOOGLE_HOST)) {

                val retrofit = Retrofit.Builder()
                   .baseUrl(constants.BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()

                val service = retrofit.create(ApiServices::class.java)

                service.getUser(editTextUserLogin.text.toString()).enqueue(object : Callback<BaseModel> {
                       override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>){

                           if (response.body()?.result?.isEmpty()!!) {
                               btnContinue.isEnabled = true
                               val commonFunctions = CommonFunctions()
                               alert.setTitle(constants.ERROR_TITLE)
                               alert.setMessage(
                                   commonFunctions.getErrorMessage(
                                       "403",
                                       ""
                                   )
                               )
                               alert.setPositiveButton(
                                   "Confirmar", null
                               )
                               alert.show()
                           } else {

                                validatePassword(editTextPassLogin.text.toString(), response.body())
                          }


                        }
                        override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                            alert.setTitle(constants.ERROR_TITLE)
                            alert.setMessage(commonFunctions.getErrorMessage("501", ""))
                            alert.setPositiveButton(
                                "Confirmar", null
                            )
                            alert.show()
                            btnContinue.isEnabled = true
                        }
                })
            }else{
                btnContinue.isEnabled = true
                val commonFunctions = CommonFunctions()
                alert.setTitle(constants.ERROR_TITLE)
                alert.setMessage(commonFunctions.getErrorMessage("402", ""))
                alert.setPositiveButton(
                    "Confirmar", null
                )
                alert.show()
            }
        }


        btnCancel.setOnClickListener {
            linearLayout.isVisible = false
            linearLayoutButtons.isVisible = true
            linearLayoutButtonLogin.isVisible = false
            editTextPassLogin.setText("")
            editTextUserLogin.setText("")
        }

        seePass.setOnClickListener {
            editTextPassLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            seePass.isVisible = false
            NoseePassword.isVisible = true
        }

        noSeePass.setOnClickListener {
            editTextPassLogin.setTransformationMethod(PasswordTransformationMethod.getInstance())
            seePass.isVisible = true
            NoseePassword.isVisible = false
        }

    }

    fun validatePassword(passInput: String, user: BaseModel?){
        if(user!!.result.get(0).password.equals(passInput)){
            val intent = Intent(applicationContext ,MainActivity::class.java)
            intent.putExtra("name",user!!.result.get(0).firstName)
            intent.putExtra("lastName",user!!.result.get(0).lastName)
            intent.putExtra("email",user!!.result.get(0).email)
            intent.putExtra("gender",user!!.result.get(0).gender)
            intent.putExtra("date",user!!.result.get(0).date)
            startActivityForResult(intent,constants.REQUEST_CODE)
            finish()
        }else{
            val btnContinue : Button = findViewById(R.id.continu)
            btnContinue.isEnabled = true
            val alert = AlertDialog.Builder(this)
            alert.setTitle(constants.ERROR_TITLE)
            alert.setMessage(commonFunctions.getErrorMessage("405",""))
            alert.setPositiveButton(
                "Confirmar",null)
            alert.show()
        }
    }


}


