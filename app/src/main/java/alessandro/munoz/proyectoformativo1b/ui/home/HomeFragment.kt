package alessandro.munoz.proyectoformativo1b.ui.home

import alessandro.munoz.proyectoformativo1b.Login
import alessandro.munoz.proyectoformativo1b.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import alessandro.munoz.proyectoformativo1b.databinding.FragmentHomeBinding
import android.app.AlertDialog
import android.content.Intent
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintSet.Layout

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val btnLogout = root.findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            val context = requireContext()

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Cerrar Sesión")
            builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")

            builder.setPositiveButton("Aceptar"){ dialog, which ->
                val Login = Intent(context, Login::class.java)
                startActivity(Login)
                requireActivity().finish()
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancelar"){ dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}