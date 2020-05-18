package com.sebastian.osorios.udea.atlas.Activitys

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.Constants
import kotlinx.android.synthetic.main.activity_landing.*
import android.text.method.*
import android.view.View
import androidx.annotation.RequiresApi
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.sebastian.osorios.udea.atlas.Util.CheckInternetConexion
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.annotations.NotNull
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class LandingActivity : AppCompatActivity() {

    private lateinit var LoginFacebook : LoginButton
    private var callbackManager : CallbackManager? = null
    val constants = Constants()
    lateinit var auth : FirebaseAuth
    lateinit var firebaseAuthListener : FirebaseAuth.AuthStateListener
    val TAG : String = "LANDING_ACTIVITY"
    lateinit var email : String
    lateinit var progressDialog : ProgressDialog
    lateinit var executor : ExecutorService
    lateinit var alert : AlertDialog.Builder
    lateinit var editTextUserLogin: EditText
    lateinit var editTextPassLogin: EditText
    lateinit var btnContinue: Button
    private val landingActivity : LandingActivity = this
    private val checkInternetConexion = CheckInternetConexion()

    @RequiresApi(Build.VERSION_CODES.P)
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
        btnContinue  = findViewById(R.id.continu)
        val btnCancel: Button = findViewById(R.id.cancel)
        val linearLayout: LinearLayout = findViewById(R.id.linearLyaout)
        val linearLayoutButtons: LinearLayout = findViewById(R.id.linearLyaoutButtons)
        val linearLayoutButtonLogin: LinearLayout = findViewById(R.id.buttonLogin)
        editTextUserLogin = findViewById(R.id.userLogin)
        editTextPassLogin = findViewById(R.id.passLogin)
        var seePass: ImageView = findViewById(R.id.seePassword)
        var noSeePass: ImageView = findViewById(R.id.NoseePassword)
        val huell : CircleImageView = findViewById(R.id.huella)
        alert = AlertDialog.Builder(this)

        btnLogIn.setOnClickListener {
            linearLayout.isVisible = true
            linearLayoutButtonLogin.isVisible = true
            linearLayoutButtons.isVisible = false
        }

        btnRegister.setOnClickListener {
            val intento = Intent(this, RegisterInAtlas::class.java)
            startActivity(intento)
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
                handleFacebookAccesToken(result!!.accessToken)
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
        if(checkInternetConexion.isConnectedToThisServer(constants.GOOGLE_HOST)){
            if(resultCode.equals(Activity.RESULT_OK)){
                callbackManager?.onActivityResult(requestCode, resultCode, data)
            }
        }else{
            alertShow("402")
        }
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
        val credential : AuthCredential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(credential).addOnCompleteListener(this, object : OnCompleteListener<AuthResult>{
            override fun onComplete(task: Task<AuthResult>) {

                if(!task.isSuccessful){
                    Toast.makeText(applicationContext,"Error en login",Toast.LENGTH_LONG).show()
                }
            }


        })
    }


    private fun goToMainActivity(){
        val intento : Intent = Intent(applicationContext,MainActivity::class.java)
        intento.putExtra("auth","fb")
        intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intento)
    }

    private fun goToMain(user: FirebaseUser){
        val email : String = user.email.toString()
        val intent = Intent(applicationContext ,MainActivity::class.java)
        intent.putExtra("auth","email")
        intent.putExtra("email",email)
        startActivityForResult(intent,constants.REQUEST_CODE)
        finish()
    }

    private fun gotToMainWhitFingerprint(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("auth", "finger")
        startActivity(intent)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun loginFingerprint(view: View) {
        showProgrresDialog()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getSystemService(Context.FINGERPRINT_SERVICE) == null){
                alertShow("413")
            }else{
                val biometricPrompt: BiometricPrompt

                executor = Executors.newSingleThreadExecutor()
                biometricPrompt = BiometricPrompt.Builder(this)
                    .setTitle("Autenticación Atlas")
                    .setSubtitle("Huella Digital")
                    .setDescription("Utiliza Tu Huella Para Ingresar.")
                    .setNegativeButton("Cancelar", executor, object : DialogInterface.OnClickListener {
                        override fun onClick(dialogInterface: DialogInterface?, i: Int) {

                        }

                    }).build()

                biometricPrompt.authenticate(CancellationSignal(), executor,
                    @RequiresApi(Build.VERSION_CODES.P)
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                            landingActivity.runOnUiThread(object : Runnable {
                                override fun run() {
                                    gotToMainWhitFingerprint()
                                }
                            })
                        }

                    })
            }
        }else{
            alertShow("412")
        }


    }


    private fun alertShow(code : String){
        progressDialog.dismiss()
        val commonFunctions = CommonFunctions()
        alert.setTitle(constants.ERROR_TITLE)
        alert.setMessage(
            commonFunctions.getErrorMessage(code)
        )
        alert.setPositiveButton("Confirmar",null)
        alert.show()
    }

    private fun showProgrresDialog(){
        progressDialog  = ProgressDialog(this)
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun continueLogin(view: View) {
        showProgrresDialog()
        if(checkInternetConexion.isConnectedToThisServer(constants.GOOGLE_HOST)){
            if(editTextUserLogin.text.length != 0  && editTextPassLogin.text.length != 0){
                btnContinue.isEnabled = false
                val email: String = editTextUserLogin.text.toString()
                auth.signInWithEmailAndPassword(email, editTextPassLogin.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user: FirebaseUser? = auth.currentUser
                            goToMain(user!!)
                        } else {
                            if (task.exception!!.message.equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                                btnContinue.isEnabled = true
                                alertShow("403")
                            } else {
                                btnContinue.isEnabled = true
                                alertShow("405")
                            }
                        }
                    }
            }else{
                alertShow("406")
            }
        }else{
            alertShow("402")
        }
    }
}