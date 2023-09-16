package site.soymegh.controlrifas.data.config

import androidx.room.Database
import androidx.room.RoomDatabase
import site.soymegh.controlrifas.data.dao.DaoRegister
import site.soymegh.controlrifas.data.entities.Register

@Database(
    entities = [Register::class],
    version = 1
)

abstract class DataBaseControl : RoomDatabase() {
    abstract fun daoRegister() : DaoRegister
}