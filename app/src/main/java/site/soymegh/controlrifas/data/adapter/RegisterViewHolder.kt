package site.soymegh.controlrifas.data.adapter

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import site.soymegh.controlrifas.data.config.ControlApplication
import site.soymegh.controlrifas.data.entities.Register
import site.soymegh.controlrifas.databinding.ItemListBinding


class RegisterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemListBinding.bind(view)
    var onItemDeleteListener: OnItemDeleteListener? = null


    fun render(register: Register){
        binding.TvNumber.text =register.id.toString()
        binding.TvName.text = register.nombre
        binding.TvLists.text = register.lista
        binding.TvActions.text = register.acciones
        binding.TvTotal.text = register.monto.toString()
        binding.TvDate.text = register.fecha.toString()

        binding.BtnDelete.setOnClickListener {
            mostrarDialogoEliminar(register)

        }

    }

    private fun mostrarDialogoEliminar(register: Register) {
        val alertDialogBuilder = AlertDialog.Builder(itemView.context)
        alertDialogBuilder.setTitle("Eliminar Registro")
        alertDialogBuilder.setMessage("¿Estás seguro de que quieres eliminar este registro?")
        alertDialogBuilder.setPositiveButton("Sí") { dialog, _ ->
            eliminarRegistro(register)
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun eliminarRegistro(register: Register) {
        val daoRegister = ControlApplication.db.daoRegister()

        GlobalScope.launch {
            val daoRegister = ControlApplication.db.daoRegister()

            GlobalScope.launch {
                val numRowsDeleted = daoRegister.delete(register)
                if (numRowsDeleted > 0) {
                    // Registro eliminado exitosamente
                    binding.root.post {
                        // Actualizar la UI después de eliminar el registro
                        binding.root.visibility = View.GONE
                        onItemDeleteListener?.onItemDelete(register)
                    }
                } else {
                    // No se pudo eliminar el registro
                    Toast.makeText(itemView.context, "No se pudo eliminar el registro", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    interface OnItemDeleteListener {
        fun onItemDelete(register: Register)
    }
}


