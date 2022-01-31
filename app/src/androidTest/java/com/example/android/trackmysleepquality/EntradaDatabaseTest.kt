package com.example.android.trackmysleepquality

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.EntradaDatabase
import com.example.android.trackmysleepquality.database.EntradaDatabaseDao
import com.example.android.trackmysleepquality.database.Entrada
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EntradaDatabaseTest {

    private lateinit var entradaDao: EntradaDatabaseDao
    private lateinit var db: EntradaDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, EntradaDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        entradaDao = db.entradaDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
/*
    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = Entrada()
        entradaDao.insert(night)
        val tonight = entradaDao.getTonight()
        assertEquals(tonight?.sleepQuality, -1)
    }

*/
}


