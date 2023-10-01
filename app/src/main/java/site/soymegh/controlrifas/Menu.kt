package site.soymegh.controlrifas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import site.soymegh.controlrifas.data.adapter.AdapterRegister
import site.soymegh.controlrifas.data.entities.Register
import site.soymegh.controlrifas.databinding.ActivityMenuBinding
import site.soymegh.controlrifas.models.Dialog
import java.security.KeyStore.TrustedCertificateEntry


class Menu : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var firebaseAuth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    var sesionCerrada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.RvList
        recyclerView.layoutManager = LinearLayoutManager(this)

        initApp()
        getRegisters()

        onCreateOptionsMenu(binding.toolbar.menu)

    }

    private fun initApp() {
        firebaseAuth = Firebase.auth

        binding.toolbar.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
            Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                     R.id.MnuNewUser -> {
                         val intent = Intent(this@Menu, NewUser::class.java)
                         startActivity(intent)
                         return true
                     }
                    R.id.MnuSalir -> {
                        signOut()
                        sesionCerrada = true;
                        return true
                    }
                    R.id.MnuChangePw->{
                        val intent = Intent(baseContext, CambiarPw::class.java)
                        startActivity(intent)
                        return true
                    }

                }
                return false
            }
        })

        binding.BtnAdd.setOnClickListener {
            val intent = Intent(this, SaveRegister::class.java)
            startActivity(intent)
        }

        binding.TieFind.setOnClickListener {
            if (binding.TieFind.text.toString().isEmpty()) {
                getRegisters()
            } else {
                val nombre = binding.TieFind.text.toString()
                getRegisters(nombre)
            }
        }

    }

    private fun selSearch(): Int {
        val radioGroup = binding.rgSearch
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId

        return when (selectedRadioButtonId) {
            R.id.rbtEncargado -> 0
            R.id.rbtLista -> 1
            else -> -1
        }
    }

    override fun onBackPressed() {
        if (sesionCerrada) {
            // No permitir volver a la vista cerrada
            finish()
        } else {
           // super.onBackPressed();
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun signOut() {
        firebaseAuth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun getRegisters() {

        // val db = FirebaseFirestore.getInstance()
        val rifasRef = db.collection("rifas")

        rifasRef.orderBy("nombre").get().addOnSuccessListener { result ->

            val rifas = mutableListOf<Register>()
            var i = 1

            var montoTotal = 0.00
            for (document in result) {
                val documentId = document.id
                val nombre: String? = document.getString("nombre")
                val lista: String? = document.getString("lista")
                val acciones: String? = document.getString("acciones")
                val monto: Double? = document.getDouble("monto")
                val fecha: String? = document.getString(("fecha"))
                montoTotal = montoTotal!! + monto!!
                binding.tvMontoTotal.text = "Monto Neto: C$ $montoTotal"
                rifas.add(Register(i, documentId, nombre!!, lista!!, acciones!!, monto!!, fecha!!))
                i += 1
            }
            val adapter = AdapterRegister(rifas)
            recyclerView.adapter = adapter
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error: $exception", Toast.LENGTH_LONG).show()
        }
    }

    private fun getRegisters(dato: String) {

        // val db = FirebaseFirestore.getInstance()
        val rifasRef = db.collection("rifas")

        val sel = selSearch()

        var campo = ""

        if (sel == 0) {
            campo = "nombre"

        } else if (sel == 1) {
            campo = "lista"
        } else {
            //Toast.makeText(this, "Debe seleccionar la búsqueda", Toast.LENGTH_LONG).show()
            val mensaje = Dialog(this)
            mensaje.showDialog("Advertencia", "Debe seleccionar el tipo de búsqueda")
            binding.rbtEncargado.isChecked= true
            return
        }
        var query = rifasRef.orderBy("$campo")
        query = query.whereGreaterThanOrEqualTo("$campo", dato)
            .whereLessThan("$campo", dato + "\uf8ff")
        query.get().addOnSuccessListener { result ->

            val rifas = mutableListOf<Register>()
            var i = 1
            var montoTotal :Double?=0.0
            for (document in result) {
                val documentId = document.id
                val nombre: String? = document.getString("nombre")
                val lista: String? = document.getString("lista")
                val acciones: String? = document.getString("acciones")
                val monto: Double? = document.getDouble("monto")
                val fecha: String? = document.getString(("fecha"))
                montoTotal = montoTotal!! + monto!!
                binding.tvMontoTotal.text = "Monto Neto: C$ $montoTotal"
                rifas.add(Register(i, documentId, nombre!!, lista!!, acciones!!, monto!!, fecha!!))
                i += 1
            }
            val adapter = AdapterRegister(rifas)
            recyclerView.adapter = adapter
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error: $exception", Toast.LENGTH_LONG).show()
        }
    }


}