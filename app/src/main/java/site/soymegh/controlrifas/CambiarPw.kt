package site.soymegh.controlrifas

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import site.soymegh.controlrifas.databinding.ActivityCambiarPwBinding

class CambiarPw : AppCompatActivity() {
    private lateinit var binding: ActivityCambiarPwBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarPwBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= Firebase.auth
        initApp()
    }

    private fun initApp() {
        binding.BtnSend.setOnClickListener {
            val email = binding.TieNEmail.text.toString()
            Log.e("jadg", email)
            sendPwReset(email)
        }
    }

    private fun sendPwReset(email: String) {
        Log.e("jadg", "Voy a probar enviar email")
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener() { task ->
            Log.e("jadg", "Estoy dentro")
            if (task.isSuccessful) {
                Toast.makeText(baseContext, "Se envio un email a tu cuenta", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(baseContext, "Error al reiniciar contraseña", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


}