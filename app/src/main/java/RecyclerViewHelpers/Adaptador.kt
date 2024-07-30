package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.Pacientes
import alessandro.munoz.proyectoformativo1b.R
import alessandro.munoz.proyectoformativo1b.expediente
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Adaptador(var Datos: List<Pacientes>): RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
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