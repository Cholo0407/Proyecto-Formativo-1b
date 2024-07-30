package alessandro.munoz.proyectoformativo1b

import Modelo.ClaseConexion
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombreRegistro = findViewById<EditText>(R.id.txtNombreRegistro)
        val txtUsuario = findViewById<EditText>(R.id.txtUsuario)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnRegistrar = findViewById<Button>(R.id.btnRegister)
        val imgBack = findViewById<ImageView>(R.id.imgBack1)


        btnRegistrar.setOnClickListener {
            val nombre = txtNombreRegistro.text.toString()
            val usuario = txtUsuario.text.toString()
            val contrasena = txtContrasena.text.toString()

            var validacion = false

            if (nombre.isEmpty()) {
                txtNombreRegistro.error = "Ingrese su nombre"
                validacion = true
            } else{
                txtNombreRegistro.error = null
            }

            if (usuario.isEmpty()) {
                txtUsuario.error = "Ingrese su usuario"
                validacion = true
            } else{
                txtUsuario.error = null
            }

            if (contrasena.isEmpty()) {
                txtContrasena.error = "Ingrese su contraseña"
                validacion = true
            } else if (contrasena.length < 8) {
                txtContrasena.error = "La contraseña debe tener al menos 8 caracteres"
                validacion = true
            } else{
                txtContrasena.error = null
            }

            if(!validacion) {
                try {
                    GlobalScope.launch(Dispatchers.IO) {
                        val objConexion = ClaseConexion().cadenaConexion()

                        val crearUsuario = objConexion?.prepareStatement("INSERT INTO usuarios VALUES(?, ?, ?, ?)")!!
                        crearUsuario.setString(1, UUID.randomUUID().toString())
                        crearUsuario.setString(2, txtNombreRegistro.text.toString())
                        crearUsuario.setString(3, txtUsuario.text.toString())
                        crearUsuario.setString(4, txtContrasena.text.toString())
                        crearUsuario.executeUpdate()

                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Register, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            txtNombreRegistro.text.clear()
                            txtUsuario.text.clear()
                            txtContrasena.text.clear()
                        }
                    }

                } catch (e: Exception) {
                    println("el error es: $e")
                }
            } else {

            }


        }

        imgBack.setOnClickListener {
            finish()
        }

    }
}