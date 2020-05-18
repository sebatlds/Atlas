package com.sebastian.osorios.udea.atlas.Activitys

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.database.FirebaseDatabase
import com.sebastian.osorios.udea.atlas.Models.User.Usuario
import com.sebastian.osorios.udea.atlas.R
import com.sebastian.osorios.udea.atlas.Util.DatePickerFragment
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_edit_perfil.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.tasks.Continuation
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sebastian.osorios.udea.atlas.Util.CommonFunctions
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import kotlin.collections.HashMap

class EditPerfil : AppCompatActivity() {

    lateinit var email: String
    lateinit var usuario: Usuario
    lateinit var imageView: CircleImageView
    private val CAPTURE_IMAGE_REQUEST = 1
    private lateinit var mCurrentPhotoPath: String
    private var imageUri : Uri? = null
    lateinit var progressDialog: ProgressDialog


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil)
        email = intent?.getStringExtra("email").toString()

        var checkMenEdit : RadioButton = findViewById(R.id.edit_chel_men)
        var checkWomenEdit : RadioButton = findViewById(R.id.edit_chel_women)
        var nameEdit : EditText = findViewById(R.id.edit_name)
        var lastName : EditText = findViewById(R.id.edit_last_name)
        var dateEdit : EditText = findViewById(R.id.edit_date)
        imageView = findViewById(R.id.image_edit_perfil)
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef = database.getReference("usuarios")

        if(!intent.getStringExtra("image").equals("null")){
            imageUri = intent.getStringExtra("image").toUri()
        }
        usuario = Usuario(
            intent?.getStringExtra("id").toString(),
            intent?.getStringExtra("email").toString(),
            intent?.getStringExtra("name").toString(),
            intent?.getStringExtra("lastName").toString(),
            intent?.getStringExtra("date").toString(),
            intent?.getStringExtra("gender").toString(),
            imageUri.toString()
        )

        checkMenEdit.setOnClickListener {
            checkMenEdit.isChecked = true
            checkWomenEdit.isChecked = false
        }

        checkWomenEdit.setOnClickListener {
            checkMenEdit.isChecked = false
            checkWomenEdit.isChecked = true
        }

        nameEdit.hint = usuario.name
        lastName.hint = usuario.lastName
        dateEdit.hint = usuario.date
        if(imageUri != null){
            Picasso.get().load(imageUri).into(imageView)
        }else{
            imageView.setImageResource(R.drawable.images)
        }
        if("Hombre".equals(usuario.gender)){
            checkMenEdit.isChecked = true
            checkWomenEdit.isChecked = false
        }else{
            checkMenEdit.isChecked = false
            checkWomenEdit.isChecked = true
        }

        btn_save_edit.setOnClickListener {
            var gender : String
            var name : String
            var lastNameEdit : String
            var date : String
            var image : String = "null"
            if(nameEdit.text.toString().equals("")){
                name = usuario.name
            }else{
                name = nameEdit.text.toString()
            }
            if(lastName.text.toString().equals("")){
                lastNameEdit = usuario.lastName
            }else{
                lastNameEdit = lastName.text.toString()
            }
            if(dateEdit.text.toString().equals("")){
                date = usuario.date
            }else{
                date = dateEdit.text.toString()
            }
            if(checkMenEdit.isChecked){
                gender = "Hombre"
            }else{
                gender = "Mujer"
            }
            if(!imageUri!!.equals(null)){
                image = imageUri.toString()
            }

            val childUpdates = HashMap<String,Any>()
            childUpdates["email"]= email
            childUpdates["name"]=name
            childUpdates["lastName"]=lastNameEdit
            childUpdates["date"]=date
            childUpdates["gender"]=gender
            childUpdates["image"]=imageUri.toString()
            myRef.child(usuario.id).updateChildren(childUpdates)
            Toast.makeText(this,"Actualización correcta",Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext ,MainActivity::class.java)
            intent.putExtra("auth","email")
            intent.putExtra("email",email)
            startActivity(intent)
            finish()
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


    fun onClick(view: View) {
        cargarImagen()
    }

    fun cargarImagen() {
        var opciones: Array<String> = arrayOf("Tomar Foto", "Cargar Imagen", "Cancelar")
        val alertOpciones: AlertDialog.Builder = AlertDialog.Builder(this)
        alertOpciones.setTitle("Seleccione una opción")
        alertOpciones.setItems(opciones, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, i: Int) {
                if (opciones[i].equals("Tomar Foto")) {
                    tomarFoto()
                } else if (opciones[i].equals("Cargar Imagen")) {
                    val intent: Intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    intent.setType("image/")
                    startActivityForResult(
                        Intent.createChooser(intent, "Seleccione la Aplicación"),
                        10
                    )
                } else {
                    dialog!!.dismiss()
                }
            }
        })
        alertOpciones.show()
    }

    private fun tomarFoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAPTURE_IMAGE_REQUEST)
        }else{
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)
                }
            }
        }





    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        progressDialog  = ProgressDialog(this)
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val storage = FirebaseStorage.getInstance().reference
            val filePath = storage.child("usuarios/"+usuario.id+".jpg")
            if(requestCode == 10){
                imageUri = data!!.data!!
                val parcelFileDescriptor = contentResolver.openFileDescriptor(imageUri!!,"r")
                val fileDescriptor = parcelFileDescriptor?.fileDescriptor
                val imageBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor!!.close()
                val bytes = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes)
                updateToStorage(imageBitmap, filePath)
            }else if(requestCode == CAPTURE_IMAGE_REQUEST){
                imageView.isDrawingCacheEnabled = true
                imageView.buildDrawingCache()
                val imageBitmap : Bitmap = data?.extras?.get("data") as Bitmap
                updateToStorage(imageBitmap, filePath)

            }else{
                displayMessage(baseContext, "Request cancelled or something went wrong.")
            }


        }else{
            progressDialog.dismiss()
        }


    }



    private fun displayMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


    private fun updateToStorage(
        imageBitmap: Bitmap,
        filePath: StorageReference
    ) {
        val bytes = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes)
        val dat = bytes.toByteArray()
        val uploadTask = filePath.putBytes(dat)
        val urlTask = uploadTask.continueWithTask(Continuation<com.google.firebase.storage.UploadTask.TaskSnapshot, com.google.android.gms.tasks.Task<android.net.Uri>> { task ->
            if(!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation filePath.downloadUrl
        }).addOnCompleteListener { task ->
            if(task.isSuccessful){
                imageUri  = task.result!!
                Picasso.get().load(imageUri).into(imageView)
                do {
                    progressDialog.dismiss()
                } while(imageView.drawable != null)


            }else{
                val commonFunctions = CommonFunctions()
                progressDialog.dismiss()
                val alert: AlertDialog.Builder = AlertDialog.Builder(this)
                alert.setTitle("Error")
                alert.setMessage(commonFunctions.getErrorMessage("407"))
                alert.setPositiveButton("Aceptar",null)
            }

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            CAPTURE_IMAGE_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                        takePictureIntent.resolveActivity(packageManager)?.also {
                            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)
                        }
                    }
                } else {
                    Toast.makeText(this,"NO SE CONCEDIO EL PERMISO PARA ACCEDER A LA CAMARA",Toast.LENGTH_SHORT).show()
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
                Toast.makeText(this,"NO SE",Toast.LENGTH_SHORT).show()

            }
        }
    }
}
