package site.soymegh.controlrifas.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "registros")
data class Register(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "lista") var lista: String,
    @ColumnInfo(name = "acciones") var acciones: String,
    @ColumnInfo(name = "monto") var monto: Double,
    @ColumnInfo(name = "fecha") var fecha: String

)