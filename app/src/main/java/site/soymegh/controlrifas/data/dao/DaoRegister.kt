package site.soymegh.controlrifas.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import site.soymegh.controlrifas.data.entities.Register

@Dao
interface DaoRegister {
    @Query("SELECT * FROM registros ORDER BY nombre ASC")
    suspend fun getAll(): List<Register>

    @Query("SELECT * FROM registros WHERE nombre LIKE  '%' || :name || '%'")
    suspend fun getByName(name:String): List<Register>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(register: List<Register>):List<Long>

    @Update
    suspend fun update(register: Register):Int

    @Delete
    suspend fun delete(register: Register):Int
}