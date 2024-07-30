package alessandro.munoz.proyectoformativo1b

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtUsuarioLogin = findViewById<EditText>(R.id.txtUsuarioLogin)
        val txtContrasenaLogin = findViewById<EditText>(R.id.txtContrasenaLogin)
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val btnIrARegistro = findViewById<Button>(R.id.btnIrARegistro)

        btnIrARegistro.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

        btnIniciarSesion.setOnClickListener {
            val usuario = txtUsuarioLogin.text.toString()
            val contrasena = txtContrasenaLogin.text.toString()

            var validacion = false

            if (usuario.isEmpty()) {
                txtUsuarioLogin.error = "Este campo es obligatorio"
                validacion = true
            }else {
                txtUsuarioLogin.error = null

            }

            if (contrasena.isEmpty()) {
            txtContrasenaLogin.error = "Este campo es obligatorio"
            validacion = true
            } else {
                txtContrasenaLogin.error = null
            }


            try {
                GlobalScope.launch(Dispatchers.IO) {
                    if (!validacion) {
                        val objConexion = ClaseConexion().cadenaConexion()

                        val login = objConexion?.prepareStatement("Select * from usuarios where usuario = ? and contrasena = ?")!!
                        login.setString(1, txtUsuarioLogin.text.toString())
                        login.setString(2, txtContrasenaLogin.text.toString())
                        val rs = login.executeQuery()

                        if (rs.next()) {
                            val intent = Intent(this@Login,MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@Login, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }

            } catch (e: Exception) {
                println("el error es: $e")
            }
        }
    }
}