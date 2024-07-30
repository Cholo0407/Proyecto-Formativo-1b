package alessandro.munoz.proyectoformativo1b

import Modelo.ClaseConexion
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [anadirPacientes.newInstance] factory method to
 * create an instance of this fragment.
 */
class anadirPacientes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_anadir_pacientes, container, false)

        val txtNombre = root.findViewById<EditText>(R.id.txtNombre)
        val txtApellido = root.findViewById<EditText>(R.id.txtApellido)
        val txtEnfermedad = root.findViewById<EditText>(R.id.txtEnfermedad)
        val txtMedicamento = root.findViewById<EditText>(R.id.txtMedicamento)
        val txtFechaNacimiento = root.findViewById<EditText>(R.id.txtFechaNacimiento)
        val txtHoraMedicamento = root.findViewById<EditText>(R.id.txtHoraMedicina)
        val txtnumCuarto = root.findViewById<EditText>(R.id.txtNumCuarto)
        val txtnumCama = root.findViewById<EditText>(R.id.txtNumCama)
        val txtEdad = root.findViewById<EditText>(R.id.txtEdad)
        val btnGuardar = root.findViewById<Button>(R.id.btnRegistrar)

        btnGuardar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val apellido = txtApellido.text.toString()
            val enfermedad = txtEnfermedad.text.toString()
            val medicamento = txtMedicamento.text.toString()
            val fechaNacimiento = txtFechaNacimiento.text.toString()
            val horaMedicamento = txtHoraMedicamento.text.toString()
            val numCuarto = txtnumCuarto.text.toString()
            val numCama = txtnumCama.text.toString()
            val edad = txtEdad.text.toString()

            var validacion = false

            //Validaciones de campos
            if (nombre.isEmpty()){
                txtNombre.error = "Ingrese un nombre"
                validacion= true
            } else {
                txtNombre.error = null
            }

            if (apellido.isEmpty()){
                txtApellido.error = "Ingrese un apellido"
                validacion= true
            } else {
                txtApellido.error = null
            }

            if (enfermedad.isEmpty()){
                txtEnfermedad.error = "Ingrese una enfermedad"
                validacion= true
            } else {
                txtEnfermedad.error = null
            }

            if (medicamento.isEmpty()){
                txtMedicamento.error = "Ingrese un medicamento"
                validacion= true
            } else {
                txtMedicamento.error = null
            }

            if (fechaNacimiento.isEmpty()){
                txtFechaNacimiento.error = "Ingrese una fecha de nacimiento"
                validacion= true
            } else {
                txtFechaNacimiento.error = null
            }

            if (horaMedicamento.isEmpty()){
                txtHoraMedicamento.error = "Ingrese una hora de medicamento"
                validacion= true
            } else {
                txtHoraMedicamento.error = null
            }

            if (numCuarto.isEmpty()){
                txtnumCuarto.error = "Ingrese un numero de cuarto"
                validacion= true
            } else {
                txtnumCuarto.error = null
            }

            if (numCama.isEmpty()){
                txtnumCama.error = "Ingrese un numero de cama"
                validacion= true
            } else {
                txtnumCama.error = null
            }

            if (edad.isEmpty()){
                txtEdad.error = "Ingrese una edad"
                validacion= true
            } else {
                txtEdad.error = null
            }

            if (!edad.matches(Regex("[0-9]+"))) {
                txtEdad.error = "La edad solo puede contener números"
                validacion = true
            } else if (edad.length > 3) {
                txtEdad.error = "La edad no puede contener más de 3 caracteres"
                validacion = true
            } else {
                txtEdad.error = null
            }

            if (!validacion){
                try {
                    GlobalScope.launch(Dispatchers.IO) {
                        val objConexion = ClaseConexion().cadenaConexion()

                        val crearPaciente = objConexion?.prepareStatement("Insert INTO Pacientes Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")!!
                        crearPaciente.setString(1, UUID.randomUUID().toString())
                        crearPaciente.setString(2, txtNombre.text.toString())
                        crearPaciente.setString(3, txtApellido.text.toString())
                        crearPaciente.setInt(4, txtEdad.text.toString().toInt())
                        crearPaciente.setString(5, txtEnfermedad.text.toString())
                        crearPaciente.setInt(6, txtnumCuarto.text.toString().toInt())
                        crearPaciente.setInt(7, txtnumCama.text.toString().toInt())
                        crearPaciente.setString(8, txtMedicamento.text.toString())
                        crearPaciente.setString(9, txtFechaNacimiento.text.toString())
                        crearPaciente.setString(10, txtHoraMedicamento.text.toString())
                        crearPaciente.executeUpdate()

                        withContext(Dispatchers.Main){
                            Toast.makeText(this@anadirPacientes.context, "Paciente registrado correctamente", Toast.LENGTH_SHORT).show()

                            txtNombre.text.clear()
                            txtApellido.text.clear()
                            txtEnfermedad.text.clear()
                            txtMedicamento.text.clear()
                            txtFechaNacimiento.text.clear()
                            txtHoraMedicamento.text.clear()
                            txtnumCuarto.text.clear()
                            txtnumCama.text.clear()
                            txtEdad.text.clear()
                        }


                    }
                } catch (e: Exception) {
                    println("El error es: $e")
                }
            }

        }



        return root
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment anadirPacientes.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            anadirPacientes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}