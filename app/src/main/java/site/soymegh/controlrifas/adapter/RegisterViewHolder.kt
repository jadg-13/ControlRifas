package site.soymegh.controlrifas.data.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import site.soymegh.controlrifas.EditRegister
import site.soymegh.controlrifas.data.entities.Register
import site.soymegh.controlrifas.databinding.ItemListBinding


class RegisterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemListBinding.bind(view)
    var onItemDeleteListener: OnItemDeleteListener? = null
    val view = view
    fun render(register: Register) {
        binding.TvNumber.text = register.id.toString()
        binding.TvName.text = register.nombre
        binding.TvLists.text = register.lista
        binding.TvActions.text = register.acciones
        binding.TvTotal.text = register.monto.toString()
        binding.TvDate.text = register.fecha

        binding.BtnDelete.setOnClickListener {
            mostrarDialogoEliminar(register)
        }

        binding.BtnEdit.setOnClickListener {
            val intent = Intent(view.context, EditRegister::class.java)
            intent.putExtra("document", register.document)
            view.context.startActivity(intent)
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
        val dbFb = FirebaseFirestore.getInstance()
        val documentId = register.document
        val documentRef = dbFb.collection("rifas").document(documentId)
        documentRef.delete()
            .addOnSuccessListener {
                binding.root.post {
                    // Actualizar la UI después de eliminar el registro
                    binding.root.visibility = View.GONE
                    onItemDeleteListener?.onItemDelete(register)
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    itemView.context,
                    "No se pudo eliminar el registro",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    interface OnItemDeleteListener {
        fun onItemDelete(register: Register)
    }
}


