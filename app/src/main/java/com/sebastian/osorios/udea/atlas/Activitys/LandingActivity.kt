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
import android.util.Log
import android.view.View
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.Login
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import org.jetbrains.annotations.NotNull
import org.json.JSONObject
import java.util.*


class LandingActivity : AppCompatActivity() {

    private lateinit var LoginFacebook : LoginButton
    private var callbackManager : CallbackManager? = null
    private var mGoogleApiClient : GoogleApiClient? = null
    var RC_SIGN_IN = 1
    val constants = Constants()
    lateinit var auth : FirebaseAuth
    lateinit var firebaseAuthListener : FirebaseAuth.AuthStateListener
    val TAG : String = "LANDING_ACTIVITY"
    private var optLog : Int = 0
    lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this)

        setContentView(R.layout.activity_landing)
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()
        callbackManager = CallbackManager.Factory.create()
        LoginFacebook = findViewById(R.id.login_with_fb)
        LoginFacebook.setReadPermissions(Arrays.asList("public_profile,email,user_birthday"))
        LoginFacebook.setReadPermissions(Arrays.asList("email"))
        val googleButton : SignInButton = findViewById(R.id.sign_in_button)
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

        val gso : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

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
            optLog = 2
            if (checkInternetConexion.isConnectedToThisServer(constants.GOOGLE_HOST)) {
                val email : String = editTextUserLogin.text.toString()
                auth.signInWithEmailAndPassword(email,editTextPassLogin.text.toString())
                    .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val user : FirebaseUser? = auth.currentUser
                        goToMain(user!!)
                    }else{
                        if(task.exception!!.message.equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                            btnContinue.isEnabled = true
                            val commonFunctions = CommonFunctions()
                            alert.setTitle(constants.ERROR_TITLE)
                            alert.setMessage(
                                commonFunctions.getErrorMessage("403", ""))
                            alert.setPositiveButton("Confirmar", null)
                            alert.show()
                        }else{
                            btnContinue.isEnabled = true
                            val commonFunctions = CommonFunctions()
                            alert.setTitle(constants.ERROR_TITLE)
                            alert.setMessage(
                                commonFunctions.getErrorMessage("405", ""))
                            alert.setPositiveButton("Confirmar", null)
                            alert.show()
                        }
                    }
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


        LoginFacebook.registerCallback(callbackManager,object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                optLog = 1
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
        auth = FirebaseAuth.getInstance()
        firebaseAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(@NotNull firebaseAuth: FirebaseAuth) {
                val user : FirebaseUser? = firebaseAuth.currentUser
                if(user != null){
                    if(optLog == 2){

                    }else{
                        goToMainActivity()
                    }

                }
            }

        }

        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this, object  : GoogleApiClient.OnConnectionFailedListener{
                    override fun onConnectionFailed(@NotNull connectionResult : ConnectionResult) {
                        Toast.makeText(this@LandingActivity,"Error Login",Toast.LENGTH_SHORT)
                    }
                })
            .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
            .build()

        googleButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                signIn()
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(optLog == 1){
            if(requestCode == RC_SIGN_IN){
                val result : GoogleSignInResult? = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if(!result!!.isSuccess){
                    val account : GoogleSignInAccount? = result.signInAccount
                    firebaseAuthListener(account!!)            }
            }else{ }
        }else {
            callbackManager?.onActivityResult(requestCode, resultCode, data)
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
        var credential : AuthCredential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(credential).addOnCompleteListener(this, object : OnCompleteListener<AuthResult>{
            override fun onComplete(task: Task<AuthResult>) {

                if(!task.isSuccessful){
                    Toast.makeText(applicationContext,"Error en login",Toast.LENGTH_LONG).show()
                }
            }
            val a = auth.currentUser

        })
    }

    fun firebaseAuthListener(account: GoogleSignInAccount){
        val credential : AuthCredential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this,object : OnCompleteListener<AuthResult>{
                override fun onComplete(@NotNull task: Task<AuthResult>) {
                    Log.d(TAG,"signInWithCredential",task.exception)
                    if(!task.isSuccessful){
                        Toast.makeText(this@LandingActivity,"Autenticacion fallo",Toast.LENGTH_LONG).show()
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

    fun signIn(){
        optLog = 1
        val sigInIntent : Intent? = Auth.GoogleSignInApi?.getSignInIntent(mGoogleApiClient)
        sigInIntent!!.putExtra("auth","fb")
        startActivityForResult(sigInIntent,RC_SIGN_IN)
    }

}