package com.sebastian.osorios.udea.atlas.Util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.*
import java.io.*

class CommonFunctions {

    fun getErrorMessage(codigo : String,body:String): String{
        when(codigo){
            "301"->{
                return "El recurso no fue modificado. Puedes usar la versión en caché."
            }
            "400"->{
                return "Solicitud incorrecta."
            }
            "401"->{
                return "Solicitud incorrecta."
            }
            "402"->{
                return "Verifique su conexion a internet."
            }
            "403"->{
                return "El usuario ingresado, no se encuentra registrado."
            }
            "404"->{
                return "El recurso solicitado no existe."
            }
            "405"->{
                return "Verifique los datos ingresados."
            }
            "422"->{
                if(body.equals("user must have atleast 11 years to have an account")){
                    return "el usuario debe tener al menos 11 años para tener una cuenta."
                }else if(body.substring(0,6).equals("Email ")){
                    return "El correo electronico ingresado ya se encuentra registrado"
                }else{
                    return "Error en los datos ingresados."
                }

            }
            "429"->{
                return "Demasiadas solicitudes. La solicitud fue rechazada debido a la limitación de la tasa."
            }
            "500"->{
                return "Error interno en el servidor."
            }
            else->{
                return "Error inesperado en la consulta."
            }
        }
    }




}

