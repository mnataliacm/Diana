package com.ncm.diana.modelos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entrada(@PrimaryKey(autoGenerate = true) val idEnt: Int = 0,
                   @ColumnInfo var iniciales:String,
                   @ColumnInfo var puntos: Int,
                   @ColumnInfo var nivel: String) {

    constructor(ini: String, pun: Int, niv: String): this(0, ini, pun, niv)

}

