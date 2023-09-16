package site.soymegh.controlrifas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import site.soymegh.controlrifas.data.config.ControlApplication
import site.soymegh.controlrifas.data.entities.Register
import site.soymegh.controlrifas.databinding.ActivitySaveRegisterBinding
import java.time.LocalDate


class SaveRegister : AppCompatActivity() {
    lateinit var binding : ActivitySaveRegisterBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

    startApp()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startApp() {
        binding.BtnSave.setOnClickListener {
            val fecha = LocalDate.now()

            try{
                var register = Register(
                        0,
                binding.TieName.text.toString(),
                binding.TieLists.text.toString(),
                binding.TieActions.text.toString(),
                binding.TieTotal.text.toString().toDouble(),
                fecha.toString()
                )
                val daoRegister = ControlApplication.db.daoRegister()
                lifecycleScope.launch {
                    daoRegister.insert(listOf(register))
                }
                Toast.makeText(this,"Registro guardado", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                //finish()
            }catch(e: Exception){
                Toast.makeText(this, "Error al intentar guardar", Toast.LENGTH_LONG).show()
            }

        }
    }
}