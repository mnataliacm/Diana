package com.ncm.diana

import android.content.Context
import androidx.room.Room

object databaseSingleton {

    private var database: Conector? = null

    fun getDatabase(contexto: Context): EntradaDao? {

        if (database==null)
            database = Room.databaseBuilder(contexto, Conector::class.java, "ranking")
                .allowMainThreadQueries()
                .build()
        //
        return database?.dao()
    }
}