package com.example.android.gestiondeentradas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entrada::class], version = 1, exportSchema = false)
abstract class EntradaDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val entradaDatabaseDao: EntradaDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: EntradaDatabase? = null

        fun getInstance(context: Context): EntradaDatabase {
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            EntradaDatabase::class.java,
                            "sleep_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}
