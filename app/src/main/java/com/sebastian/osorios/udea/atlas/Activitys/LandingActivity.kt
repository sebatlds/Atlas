package com.sebastian.osorios.udea.atlas.Activitys

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.CheckInternetConexion
import com.sebastian.osorios.udea.atlas.Util.Constants
import kotlinx.android.synthetic.main.activity_landing.*
import android.text.method.*
import com.sebastian.osorios.udea.atlas.Interfaces.UserDAO
import com.sebastian.osorios.udea.atlas.Models.User.Usuario
import com.sebastian.osorios.udea.atlas.DB.SesionRoom


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
                val email : String = editTextUserLogin.text.toString()
                val userDAO : UserDAO = SesionRoom.database.UserDAO()
                val usuario : Usuario = userDAO.searchUser(email)

                if (usuario != null) {
                    validatePassword(editTextPassLogin.text.toString(), usuario)
                } else {
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

                    }
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
    fun validatePassword(passInput: String, user: Usuario){
        if(user.password.equals(passInput)){
            val id : String = user.id.toString()
            val intent = Intent(applicationContext ,MainActivity::class.java)
            intent.putExtra("id",id)
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
