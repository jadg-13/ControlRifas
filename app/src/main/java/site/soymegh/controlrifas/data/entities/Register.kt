package site.soymegh.controlrifas.data.entities


data class Register(
    val id: Int,
    val document:String,
    val nombre: String,
    val lista: String,
    val acciones: String,
    val monto: Double,
    val fecha: String

)