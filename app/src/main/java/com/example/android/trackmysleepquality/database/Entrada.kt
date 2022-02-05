package com.example.android.trackmysleepquality.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entrada_table")
data class Entrada(
        @PrimaryKey(autoGenerate = true)
        var id_entrada: Long = 0L,

        @ColumnInfo(name = "nombre_partido")
        val partido: String = "",

        @ColumnInfo(name = "nombre_grada")
        var grada: String = ""
)
