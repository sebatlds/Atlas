package com.sebastian.osorios.udea.atlas.Util

import java.net.UnknownHostException

class CheckInternetConexion {

    fun isConnectedToThisServer(host: String): Boolean {

        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 $host")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        }
        catch (e : UnknownHostException){
            return false
        }
    }
}