package site.soymegh.controlrifas

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import site.soymegh.controlrifas.data.entities.Register
import site.soymegh.controlrifas.databinding.ActivityEditRegisterBinding
import java.time.LocalDate

class EditRegister : AppCompatActivity() {
    private lateinit var binding: ActivityEditRegisterBinding
    private val dbFb = FirebaseFirestore.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initApp()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initApp() {
        val document =  intent.getStringExtra("document").toString()

        showRegister(document)

        binding.BtnSave.setOnClickListener {
            update(document)
        }
    }

    private fun showRegister(document: String) {
        val rifasRef = dbFb.collection("rifas")
        val documentoId = document
        rifasRef.document(documentoId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {

                    val data = documentSnapshot.data
                    // Accede a los elementos del documento utilizando el mapa de datos
                    val nombre = data?.get("nombre") as? String
                    val lista = data?.get("lista") as? String
                    val acciones = data?.get("acciones") as? String
                    val monto = data?.get("monto") as? Double


                    binding.TieName.text = nombre?.toEditable()
                    binding.TieLists.text = lista?.toEditable()
                    binding.TieActions.text = acciones?.toEditable()
                    binding.TieTotal.text = monto?.toDouble().toString().toEditable()
                } else {
                    // El documento no existe
                    Log.e("jadg", "El documento no existe")
                }
            }
            .addOnFailureListener { exception ->
                // Ocurrió un error al obtener los datos del documento
                Log.e("jadg", "Error al obtener los datos del documento", exception)
            }
    }

    // Extensión para convertir una cadena en Editable
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


    @RequiresApi(Build.VERSION_CODES.O)
    private fun update(document:String){
        val fecha = LocalDate.now()
        val register = Register(
            0,
            document,
            binding.TieName.text.toString(),
            binding.TieLists.text.toString(),
            binding.TieActions.text.toString(),
            binding.TieTotal.text.toString().toDouble(),
            fecha.toString()
        )
        val docRef = dbFb.collection("rifas").document(document)
        val updates = hashMapOf<String, Any>(
            "nombre" to register.nombre,
            "lista" to register.lista,
            "acciones" to register.acciones,
            "monto" to register.monto,
            "fecha" to register.fecha
            // Agrega más campos según sea necesario
        )

        docRef.update(updates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // La actualización se realizó correctamente
                    Toast.makeText(this, "Registro actualizado", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                } else {
                    // Ocurrió un error al actualizar el documento
                    Toast.makeText(this, "Error al actualizar el registro", Toast.LENGTH_LONG).show()
                }
            }


    }
}