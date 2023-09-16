package site.soymegh.controlrifas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import site.soymegh.controlrifas.data.adapter.AdapterRegister
import site.soymegh.controlrifas.data.config.Constante
import site.soymegh.controlrifas.data.entities.Register

import site.soymegh.controlrifas.databinding.ActivityMainBinding
import site.soymegh.controlrifas.viewmodel.RegisterVM


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RegisterVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get()
        viewModel.iniciar()

        startApp()
    }


    private fun startApp() {

        binding.RvList.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }

       initRecyclerView()

        binding.BtnAdd.setOnClickListener {
            val intent = Intent(this, SaveRegister::class.java)
            intent.putExtra(Constante.OPERATION_KEY, Constante.OPERATION_INSERT )
            startActivity(intent)
            //finish()
        }

        binding.TieFind.setOnClickListener {
            val name = binding.TieFind.text.toString()
            viewModel.findByName(name)
        }
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        viewModel.controlList.observe(this, Observer {
            binding.RvList.adapter=AdapterRegister(it as MutableList<Register>)
            binding.RvList.addItemDecoration(decoration)
        })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}