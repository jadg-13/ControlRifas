package site.soymegh.controlrifas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import site.soymegh.controlrifas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: AuthStateListener
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth
        initApp()

    }

    private fun initApp() {
        binding.BtnLogin.setOnClickListener {
            if (binding.TieEmail.text.toString().isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "El email no puede quedar vacío",
                    Toast.LENGTH_LONG
                ).show()
                binding.TieEmail.requestFocus()
            } else if (binding.TiePassword.text.toString().isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "La contraseña no puede quedar vacía",
                    Toast.LENGTH_LONG
                ).show()
                binding.TiePassword.requestFocus()
            } else {
                val email = binding.TieEmail.text.toString()
                val pw = binding.TiePassword.text.toString()
                singIn(email, pw)
            }
        }

        binding.TvForgetPw.setOnClickListener {
            val intent = Intent(this, CambiarPw::class.java)
            startActivity(intent)
        }

        binding.TvNewUser.setOnClickListener {
            val intent = Intent(this, NewUser::class.java)
            startActivity(intent)
        }
    }

    private fun singIn(email: String, pw: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                val verificado = user?.isEmailVerified
                if (verificado == true) {
                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Debe verificar su cuenta", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(baseContext, "Usuario invalido", Toast.LENGTH_LONG).show()
            }

        }
    }



}
