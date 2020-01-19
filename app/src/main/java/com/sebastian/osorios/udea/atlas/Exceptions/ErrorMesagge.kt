package com.sebastian.osorios.udea.atlas.Exceptions

enum class ErrorMesagge(mensaje : String) {

    ERROR_301( "El recurso no fue modificado. Puedes usar la versión en caché."),
    ERROR_400("Solicitud incorrecta."),
    ERROR_401("Solicitud incorrecta."),
    ERROR_404("El recurso solicitado no existe."),
    ERROR_422("Error en los datos ingresados."),
    ERROR_429("Demasiadas solicitudes La solicitud fue rechazada debido a la limitación de la tasa."),
    ERROR_500( "Error interno en el servidor."),
    ERROR_11_YEARS("el usuario debe tener al menos 11 años para tener una cuenta"),
    ERROR_EMAIL("El correo electronico ingresado ya se encuentra registrado"),
    ERROR_GENERIC("Error inesperado en la consulta.");

    private val mensaje: String? = null


    open fun getMessage(): String? {
        return mensaje
    }
}