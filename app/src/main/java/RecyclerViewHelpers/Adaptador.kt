package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.Pacientes
import alessandro.munoz.proyectoformativo1b.R
import android.app.AlertDialog
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
        val item = Datos[position]
        holder.lblNombre.text = item.nombre + " " + item.apellido

        holder.btnBorrar.setOnClickListener {
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setMessage("¿Está seguro de eliminar el paciente ${item.nombre} ${item.apellido}?")

            builder.setPositiveButton("Aceptar") { dialog, wich ->
            // Eliminar el paciente
                BorrarPaciente(item.nombre, item.apellido, position)
                Toast.makeText(context, "Paciente eliminado", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("Cancelar") { dialog, wich ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }


}