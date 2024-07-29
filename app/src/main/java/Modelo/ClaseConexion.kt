package Modelo

import java.sql.DriverManager
import java.sql.Connection

class ClaseConexion {

    fun cadenaConexion(): Connection?
    {
        try {
            val ip = "jdbc:oracle:thin:@192.168.1.24:1521:xe"
            val usuario = "ProyectoFormativo"
            val contrasena = "12345"

            val conexion = DriverManager.getConnection(ip,usuario,contrasena)
            return  conexion
        }
        catch (e:Exception)
        {
            println("el error es: $e")
            return null
        }
    }
}