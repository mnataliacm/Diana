package com.ncm.diana
/**
 * Natalia Castillo Muñoz
 */
import androidx.room.*
import com.ncm.diana.modelos.Entrada

@Dao
interface EntradaDao {

    @Query("SELECT * FROM entrada ORDER BY puntos DESC, iniciales ASC")
    fun getAll():MutableList<Entrada>

    @Query("SELECT * FROM entrada WHERE idEnt = :identificador")
    fun getById(identificador: Int): Entrada

    @Insert
    fun insert(vararg entrada: Entrada)

    @Delete
    fun borrar(vararg entrada: Entrada)

    @Update
    fun actualizar(vararg entrada: Entrada)

}