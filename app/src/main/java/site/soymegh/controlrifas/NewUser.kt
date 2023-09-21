package site.soymegh.controlrifas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import site.soymegh.controlrifas.databinding.ActivityNewUserBinding
import android.graphics.Color;
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText;

class NewUser : AppCompatActivity() {
    private lateinit var binding: ActivityNewUserBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth
        initApp()
    }

    private fun initApp() {
        binding.BtnRegister.setOnClickListener {
            if (validateField()) {
                val email = binding.TieEmailUser.text.toString()
                val pw =binding.TiePasswordUser.text.toString()
                saveNewUser(email, pw)
                Toast.makeText(baseContext, "Entre a su email para terminar el registro.", Toast.LENGTH_LONG).show()
            }
        }
        binding.BtnLogin.setOnClickListener {
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun saveNewUser(email:String, pw:String){
        firebaseAuth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(this){
            task->
            if (task.isSuccessful){
                sendEmailVerification()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateField(): Boolean {
        if (binding.TieUserName.text.toString().isEmpty()) {
            binding.TieUserName.setError("Debe ingresar un nombre de usuario")
            binding.TieUserName.requestFocus()
            return false
        } else if (binding.TieEmailUser.text.toString().isEmpty()) {
            binding.TieEmailUser.setError("Debe ingresar un email")
            binding.TieEmailUser.requestFocus()
            return false
        }else if (!Patterns.EMAIL_ADDRESS.matcher(binding.TieEmailUser.text.toString()).matches()) {
            binding.TieEmailUser.setError("El correo ingresado no es válido");
            binding.TieEmailUser.requestFocus();
            return false;
        } else if (binding.TiePasswordUser.text.toString().isEmpty()) {
            binding.TiePasswordUser.setError("Contraseña no puede quedar vacía")
            binding.TiePasswordUser.requestFocus()
            return false
        } else if (binding.TieRPassword.text.toString().isEmpty()) {
            binding.TieRPassword.setError("Repita la contraseña")
            binding.TieRPassword.requestFocus()
            return false
        } else if (!validatePw(
                binding.TiePasswordUser.text.toString(),
                binding.TieRPassword.text.toString()
            )
        ) {
            binding.TieRPassword.requestFocus()
            Toast.makeText(baseContext, "Contraseñas no iguales", Toast.LENGTH_LONG).show()
            binding.TiePasswordUser.setError("Contraseñas no iguales")
            binding.TieRPassword.setError("Contraseñas no iguales");
            return false
        }

        return true
    }

    private fun validatePw(pw1: String, pw2: String): Boolean {
        return pw1 == pw2
    }

    private fun sendEmailVerification(){
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this){
            task->
            if (task.isSuccessful){

            }
        }
    }
}