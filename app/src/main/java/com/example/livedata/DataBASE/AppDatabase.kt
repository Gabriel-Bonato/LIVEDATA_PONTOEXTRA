package com.example.livedata.DataBASE

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.livedata.DAO.PersonagemDAO
import com.example.livedata.Entites.Personagem

@Database(entities = [Personagem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personagemDAO(): PersonagemDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}