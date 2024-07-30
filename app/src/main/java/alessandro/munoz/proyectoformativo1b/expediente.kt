package alessandro.munoz.proyectoformativo1b

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class expediente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_expediente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lblUUid = findViewById<TextView>(R.id.lblUUID)
        val lblNombre = findViewById<TextView>(R.id.lblNombre)
        val lblApellido = findViewById<TextView>(R.id.lblApellido)
        val lblNumCuarto = findViewById<TextView>(R.id.lblNumCuarto)
        val lblNumCama = findViewById<TextView>(R.id.lblNumCama)
        val lblEdad = findViewById<TextView>(R.id.lblEdad)
        val lblEnfermedad = findViewById<TextView>(R.id.lblEnfermedad)
        val lblMedicamentos = findViewById<TextView>(R.id.lblMedicamentos)
        val lblHoraMedicamentos = findViewById<TextView>(R.id.lblHoraMedicamentos)
        val lblFechaNacimiento = findViewById<TextView>(R.id.lblFechaNacimiento)
        val lblTelefono = findViewById<TextView>(R.id.lblTelefono)
        val lblTipoSangre = findViewById<TextView>(R.id.lblTipoSangre)
        val imgBack = findViewById<ImageView>(R.id.imgBack)

        lblUUid.text = intent.getStringExtra("uuid_paciente")
        lblNombre.text = intent.getStringExtra("nombre")
        lblApellido.text = intent.getStringExtra("apellido")
        lblTelefono.text = intent.getStringExtra("telefono")
        lblTipoSangre.text = intent.getStringExtra("tipo_sangre")
        lblNumCuarto.text = intent.getIntExtra("numero_Cuarto", 0).toString()
        lblNumCama.text = intent.getIntExtra("numero_Cama", 0).toString()
        lblEdad.text = intent.getIntExtra("edad", 0).toString()
        lblEnfermedad.text = intent.getStringExtra("enfermedad")
        lblMedicamentos.text = intent.getStringExtra("medicamentos")
        lblHoraMedicamentos.text = intent.getStringExtra("hora_medicamentos")
        lblFechaNacimiento.text = intent.getStringExtra("fecha_nacimiento")

        imgBack.setOnClickListener {
            finish()
        }


    }
}