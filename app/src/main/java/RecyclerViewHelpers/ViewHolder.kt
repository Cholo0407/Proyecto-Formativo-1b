package RecyclerViewHelpers

import alessandro.munoz.proyectoformativo1b.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val lblNombre = view.findViewById<TextView>(R.id.lblNombre)
    val btnBorrar = view.findViewById<ImageView>(R.id.imgBorrar)
    val btnEditar = view.findViewById<ImageView>(R.id.imgEditar)
    val btnExpediente = view.findViewById<ImageView>(R.id.imgExpediente)

}