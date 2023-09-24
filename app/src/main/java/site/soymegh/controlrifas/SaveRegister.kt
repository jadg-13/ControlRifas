package site.soymegh.controlrifas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import site.soymegh.controlrifas.data.entities.Register
import site.soymegh.controlrifas.databinding.ActivitySaveRegisterBinding
import java.time.LocalDate

class SaveRegister : AppCompatActivity() {
    private lateinit var binding: ActivitySaveRegisterBinding
    private val dbFb = FirebaseFirestore.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initApp()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initApp() {
        binding.BtnSave.setOnClickListener {
            saveRemote()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveRemote(){
        val fecha = LocalDate.now()
        var register = Register(
            0,
            "",
            binding.TieName.text.toString(),
            binding.TieLists.text.toString(),
            binding.TieActions.text.toString(),
            binding.TieTotal.text.toString().toDouble(),
            fecha.toString()
        )
        dbFb.collection("rifas").add(
            hashMapOf("nombre" to register.nombre,
                "lista" to register.lista,
                "acciones" to register.acciones,
                "monto" to register.monto,
                "fecha" to register.fecha)
        ).addOnSuccessListener {
            documentReference->
            Toast.makeText(this, "Registro guardado", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)

        }.addOnFailureListener{e->
            Toast.makeText(baseContext, "Error $e", Toast.LENGTH_LONG).show()
        }
    }

}