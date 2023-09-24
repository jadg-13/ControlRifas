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


class Menu : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var firebaseAuth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView

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
                    R.id.MnuHora -> {
                        //Toast.makeText(this@Menu, "hora", Toast.LENGTH_LONG).show()
                        return true
                    }

                    R.id.MnuSalir -> {
                        signOut()
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

    override fun onBackPressed() {
        return
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun signOut() {
        firebaseAuth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun getRegisters() {

       // val db = FirebaseFirestore.getInstance()
        val rifasRef = db.collection("rifas")

        rifasRef.orderBy("nombre").get().addOnSuccessListener { result ->

            val rifas = mutableListOf<Register>()
            var i = 1

            for (document in result) {
                val documentId = document.id
                val nombre: String? = document.getString("nombre")
                val lista: String? = document.getString("lista")
                val acciones: String? = document.getString("acciones")
                val monto: Double? = document.getDouble("monto")
                val fecha: String? = document.getString(("fecha"))
                rifas.add(Register(i, documentId, nombre!!, lista!!, acciones!!, monto!!, fecha!!))
                i += 1
            }
            val adapter = AdapterRegister(rifas)
            recyclerView.adapter = adapter
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error: $exception", Toast.LENGTH_LONG).show()
        }
    }

    private fun getRegisters(nombre: String) {

       // val db = FirebaseFirestore.getInstance()
        val rifasRef = db.collection("rifas")

        rifasRef.whereGreaterThanOrEqualTo("nombre", nombre)
            .whereLessThan("nombre", nombre + "\uf8ff")
            .orderBy("nombre")
            .get().addOnSuccessListener { result ->

                val rifas = mutableListOf<Register>()
                var i = 1

                for (document in result) {
                    val documentId = document.id
                    val nombre: String? = document.getString("nombre")
                    val lista: String? = document.getString("lista")
                    val acciones: String? = document.getString("acciones")
                    val monto: Double? = document.getDouble("monto")
                    val fecha: String? = document.getString(("fecha"))
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