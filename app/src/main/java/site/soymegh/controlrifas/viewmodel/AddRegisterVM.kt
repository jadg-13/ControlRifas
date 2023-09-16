package site.soymegh.controlrifas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import site.soymegh.controlrifas.data.config.Constante

class AddRegisterVM:ViewModel() {
    var id = MutableLiveData<Int>()
    var nombre = MutableLiveData<String>()
    var lista = MutableLiveData<String>()
    var acciones = MutableLiveData<String>()
    var monto = MutableLiveData<Double>()
    var fecha = MutableLiveData<String>()
    var operation = Constante.OPERATION_INSERT
    var flag = MutableLiveData<Boolean>()

    fun guardarRegistro(){
        when(operation){
            Constante.OPERATION_INSERT->{
                Log.d("jadg", "nombre ${nombre.value} monto ${monto.value}")
            }
        }
    }

}
