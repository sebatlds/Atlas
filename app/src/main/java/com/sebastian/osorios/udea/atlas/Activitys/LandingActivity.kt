package com.sebastian.osorios.udea.atlas.Activitys

import android.app.AlertDialog
import android.app.ProgressDialog
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
import android.util.Log
import android.view.View
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import org.jetbrains.annotations.NotNull
import java.util.*


class LandingActivity : AppCompatActivity() {

    private lateinit var LoginFacebook : LoginButton
    private var callbackManager : CallbackManager? = null
    val constants = Constants()
    lateinit var auth : FirebaseAuth
    lateinit var firebaseAuthListener : FirebaseAuth.AuthStateListener
    val TAG : String = "LANDING_ACTIVITY"
    private var optLog : Int = 0
    lateinit var email : String
    lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this)
        setContentView(R.layout.activity_landing)
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()
        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()
        LoginFacebook = findViewById(R.id.login_with_fb)
        LoginFacebook.setReadPermissions(Arrays.asList("email"))
        val btnLogIn: Button = findViewById(R.id.login)
        val btnRegister: Button = findViewById(R.id.register)
        val btnContinue: Button = findViewById(R.id.continu)
        val btnCancel: Button = findViewById(R.id.cancel)
        val linearLayout: LinearLayout = findViewById(R.id.linearLyaout)
        val linearLayoutButtons: LinearLayout = findViewById(R.id.linearLyaoutButtons)
        val linearLayoutButtonLogin: LinearLayout = findViewById(R.id.buttonLogin)
        var editTextUserLogin: EditText = findViewById(R.id.userLogin)
        var editTextPassLogin: EditText = findViewById(R.id.passLogin)
        var seePass: ImageView = findViewById(R.id.seePassword)
        var noSeePass: ImageView = findViewById(R.id.NoseePassword)
        val alert = AlertDialog.Builder(this)


        btnLogIn.setOnClickListener {
            linearLayout.isVisible = true
            linearLayoutButtonLogin.isVisible = true
            linearLayoutButtons.isVisible = false
        }

        btnRegister.setOnClickListener {
            val intento = Intent(this, RegisterInAtlas::class.java)
            startActivity(intento)
        }


        btnContinue.setOnClickListener {
            progressDialog  = ProgressDialog(this)
            progressDialog.show()
            progressDialog.setContentView(R.layout.progress_dialog)
            progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            if(editTextUserLogin.text.length != 0  && editTextPassLogin.text.length != 0){
                btnContinue.isEnabled = false
                optLog = 2

                val email: String = editTextUserLogin.text.toString()
                auth.signInWithEmailAndPassword(email, editTextPassLogin.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user: FirebaseUser? = auth.currentUser
                            goToMain(user!!)
                        } else {
                            progressDialog.dismiss()
                            if (task.exception!!.message.equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                                btnContinue.isEnabled = true
                                val commonFunctions = CommonFunctions()
                                alert.setTitle(constants.ERROR_TITLE)
                                alert.setMessage(
                                    commonFunctions.getErrorMessage("403", "")
                                )
                                alert.setPositiveButton("Confirmar", null)
                                alert.show()
                            } else {
                                btnContinue.isEnabled = true
                                val commonFunctions = CommonFunctions()
                                alert.setTitle(constants.ERROR_TITLE)
                                alert.setMessage(
                                    commonFunctions.getErrorMessage("405", "")
                                )
                                alert.setPositiveButton("Confirmar", null)
                                alert.show()
                            }
                        }
                    }
            }else{
                val commonFunctions = CommonFunctions()
                alert.setTitle("Error")
                alert.setMessage(
                    commonFunctions.getErrorMessage("406","")
                )
                alert.setPositiveButton("Confirmar",null)
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


        LoginFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                optLog = 2
                handleFacebookAccesToken(result!!.accessToken)
                goToMainActivity()
            }

            override fun onCancel() {
                TODO("Not yet implemented")
            }

            override fun onError(error: FacebookException?) {
                TODO("Not yet implemented")
            }


        })

        firebaseAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(@NotNull firebaseAuth: FirebaseAuth) {
                val user: FirebaseUser? = firebaseAuth.currentUser
                if (user != null) {
                    goToMainActivity()

                }
            }

        }




    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }




    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(firebaseAuthListener)
    }


    fun handleFacebookAccesToken(accessToken: AccessToken){
        var credential : AuthCredential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(credential).addOnCompleteListener(this, object : OnCompleteListener<AuthResult>{
            override fun onComplete(task: Task<AuthResult>) {

                if(!task.isSuccessful){
                    Toast.makeText(applicationContext,"Error en login",Toast.LENGTH_LONG).show()
                }
            }


        })
    }


    fun goToMainActivity(){
        val intento : Intent = Intent(applicationContext,MainActivity::class.java)
        intento.putExtra("auth","fb")
        intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intento)
    }

    fun goToMain(user: FirebaseUser){
        val email : String = user.email.toString()
        val intent = Intent(applicationContext ,MainActivity::class.java)
        intent.putExtra("auth","email")
        intent.putExtra("email",email)
        startActivityForResult(intent,constants.REQUEST_CODE)
        finish()
    }



}