package com.ncm.diana

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ncm.diana.modelos.Entrada

@Database(entities = [Entrada::class], version = 1)
abstract class Conector:RoomDatabase() {

    abstract fun dao(): EntradaDao

    companion object
    {
        @Volatile
        private var database: Conector? = null

        fun getDatabase(contexto: Context): EntradaDao? {

            return database?.dao() ?: synchronized(this)
            {
                database = Room.databaseBuilder(contexto, Conector::class.java, "ranking")
                    .allowMainThreadQueries()
                    .build()

                database?.dao()
            }

            //if (database == null)
            //    database = Room.databaseBuilder(contexto, AppDatabase::class.java, "ranking")
            //        .allowMainThreadQueries()
            //        .build()
            //
            //return database?.dao()
        }
    }
}