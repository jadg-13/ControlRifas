package site.soymegh.controlrifas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import site.soymegh.controlrifas.databinding.ActivityMainBinding
import site.soymegh.controlrifas.models.Dialog

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
                binding.TieEmail.setError("Ingrese su email.")
                binding.TieEmail.requestFocus()
            } else if (binding.TiePassword.text.toString().isEmpty()) {
                binding.TiePassword.setError("Debe ingresar su contraseña")
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

       /* binding.TvNewUser.setOnClickListener {
            val intent = Intent(this, NewUser::class.java)
            startActivity(intent)
        }*/
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
                    val mensaje = Dialog(this)
                    mensaje.showDialog("Advertencia", "Debe verificar su cuenta, revise su email")
                }
            } else {
                //Toast.makeText(baseContext, "Usuario invalido", Toast.LENGTH_LONG).show()
                val mensaje = Dialog(this)
                mensaje.showDialog("Error", "Verifique si proporciono bien el email y la contraseña")

                binding.TieEmail.requestFocus()
            }

        }
    }
}
