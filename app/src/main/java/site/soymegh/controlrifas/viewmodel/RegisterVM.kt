package site.soymegh.controlrifas.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import site.soymegh.controlrifas.data.config.ControlApplication.Companion.db
import site.soymegh.controlrifas.data.dao.DaoRegister
import site.soymegh.controlrifas.data.entities.Register


class RegisterVM() : ViewModel() {
    val controlList = MutableLiveData<List<Register>?>()
    var paramsFind = MutableLiveData<String>()

    fun iniciar() {
        viewModelScope.launch {
            controlList.value = withContext(Dispatchers.IO) {
                db.daoRegister().getAll()
            }
        }
    }

    fun add(nombre: String, lista: String, acciones: String, monto: Double, fecha: String) {
        val register = Register(0, nombre, lista, acciones, monto, fecha)


    }

    private fun save(register: Register) {
        viewModelScope.launch {
            //dao.insert(register)
        }
    }
}
