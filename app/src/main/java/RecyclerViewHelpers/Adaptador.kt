package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.Pacientes
import alessandro.munoz.proyectoformativo1b.R
import alessandro.munoz.proyectoformativo1b.expediente
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class Adaptador(var Datos: List<Pacientes>): RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    fun ActualizarVista(nombre: String, apellido: String, edad: Int, enfermedad: String, numero_Cuarto: Int, numero_Cama: Int, medicamentos: String, fecha_Nacimiento: String, hora_Medicamentos: String, uuid_paciente: String){
        val index = Datos.indexOfFirst { it.uuid_paciente == uuid_paciente }
        Datos[index].nombre = nombre
        Datos[index].apellido = apellido
        Datos[index].edad = edad
        Datos[index].enfermedad = enfermedad
        Datos[index].numero_Cuarto = numero_Cuarto
        Datos[index].numero_Cama = numero_Cama
        Datos[index].medicamentos = medicamentos
        Datos[index].fecha_Nacimiento = fecha_Nacimiento
        Datos[index].hora_Medicamentos = hora_Medicamentos
        notifyDataSetChanged()
    }

    fun EditarPaciente(nombre: String, apellido: String, edad: Int, enfermedad: String, numero_Cuarto: Int, numero_Cama: Int, medicamentos: String, fecha_Nacimiento: String, hora_medicamentos: String, uuid_paciente: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val actualizarPaciente = objConexion?.prepareStatement("UPDATE pacientes SET nombre = ?, apellido = ?, edad = ?, enfermedad = ?, numero_Cuarto = ?, numero_Cama = ?, medicamentos = ?, fecha_Nacimiento = ?, hora_medicamentos = ? WHERE uuid_paciente = ?")!!
            actualizarPaciente.setString(1, nombre)
            actualizarPaciente.setString(2, apellido)
            actualizarPaciente.setInt(3, edad)
            actualizarPaciente.setString(4, enfermedad)
            actualizarPaciente.setInt(5, numero_Cuarto)
            actualizarPaciente.setInt(6, numero_Cama)
            actualizarPaciente.setString(7, medicamentos)
            actualizarPaciente.setString(8, fecha_Nacimiento)
            actualizarPaciente.setString(9, hora_medicamentos)
            actualizarPaciente.setString(10, uuid_paciente)
            actualizarPaciente.executeUpdate()

            withContext(Dispatchers.Main){
                ActualizarVista(nombre, apellido, edad, enfermedad, numero_Cuarto, numero_Cama, medicamentos, fecha_Nacimiento, hora_medicamentos, uuid_paciente)
            }
        }
    }

    fun BorrarPaciente(nombre: String, apellido: String, posicion: Int) {
        val ListaPacientes = Datos.toMutableList()
        ListaPacientes.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()

            val borrarPaciente = objConexion?.prepareStatement("DELETE FROM pacientes WHERE nombre = ? and apellido = ?")!!
            borrarPaciente.setString(1, nombre)
            borrarPaciente.setString(2, apellido)
            borrarPaciente.executeUpdate()

            val commit = objConexion.prepareStatement("COMMIT")
            commit.executeUpdate()
        }

        Datos = ListaPacientes
        notifyDataSetChanged()
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paciente = Datos[position]
        holder.lblNombre.text = paciente.nombre + " " + paciente.apellido

        holder.btnBorrar.setOnClickListener {
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setMessage("¿Está seguro de eliminar el paciente ${paciente.nombre} ${paciente.apellido}?")

            builder.setPositiveButton("Aceptar") { dialog, wich ->
            // Eliminar el paciente
                BorrarPaciente(paciente.nombre, paciente.apellido, position)
                Toast.makeText(context, "Paciente eliminado", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("Cancelar") { dialog, wich ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        holder.btnEditar.setOnClickListener{
            val context = holder.itemView.context

            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL

            val txtNombre = EditText(context)
            layout.addView(txtNombre)
            txtNombre.setText(paciente.nombre)
            val txtApellido = EditText(context)
            layout.addView(txtApellido)
            txtApellido.setText(paciente.apellido)
            val txtEdad = EditText(context)
            layout.addView(txtEdad)
            txtEdad.setText(paciente.edad.toString())
            val txtEnfermedad = EditText(context)
            layout.addView(txtEnfermedad)
            txtEnfermedad.setText(paciente.enfermedad)
            val txtNumeroCuarto = EditText(context)
            layout.addView(txtNumeroCuarto)
            txtNumeroCuarto.setText(paciente.numero_Cuarto.toString())
            val txtNumeroCama = EditText(context)
            layout.addView(txtNumeroCama)
            txtNumeroCama.setText(paciente.numero_Cama.toString())
            val txtMedicamentos = EditText(context)
            layout.addView(txtMedicamentos)
            txtMedicamentos.setText(paciente.medicamentos)
            val txtFechaNacimiento = EditText(context)
            layout.addView(txtFechaNacimiento)
            txtFechaNacimiento.setText(paciente.fecha_Nacimiento)
            val txtHoraMedicamentos = EditText(context)
            layout.addView(txtHoraMedicamentos)
            txtHoraMedicamentos.setText(paciente.hora_Medicamentos)

            val uuid = paciente.uuid_paciente

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar Paciente")
            builder.setView(layout)

            builder.setPositiveButton("Aceptar"){ dialog, wich ->
                EditarPaciente(txtNombre.text.toString(), txtApellido.text.toString(), txtEdad.text.toString().toInt(), txtEnfermedad.text.toString(), txtNumeroCuarto.text.toString().toInt(), txtNumeroCama.text.toString().toInt(), txtMedicamentos.text.toString(), txtFechaNacimiento.text.toString(), txtHoraMedicamentos.text.toString(), uuid)
                Toast.makeText(context, "Paciente editado", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("Cancelar"){ dialog, wich ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }

        holder.btnExpediente.setOnClickListener{
            val context = holder.itemView.context

            val expediente = Intent(context, expediente::class.java)
            expediente.putExtra("uuid_paciente", paciente.uuid_paciente)
            expediente.putExtra("nombre", paciente.nombre)
            expediente.putExtra("apellido", paciente.apellido)
            expediente.putExtra("edad", paciente.edad)
            expediente.putExtra("enfermedad", paciente.enfermedad)
            expediente.putExtra("numero_Cuarto", paciente.numero_Cuarto)
            expediente.putExtra("numero_Cama", paciente.numero_Cama)
            expediente.putExtra("medicamentos", paciente.medicamentos)
            expediente.putExtra("fecha_nacimiento", paciente.fecha_Nacimiento)
            expediente.putExtra("hora_medicamentos", paciente.medicamentos)
            context.startActivity(expediente)


        }
    }


}