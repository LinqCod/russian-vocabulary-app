package com.linqcod.russianvocabulary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        //TODO: Learn what's volatile
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase {
            val tempDatabase = INSTANCE
            if(tempDatabase != null) {
                return tempDatabase
            }
            //TODO: what's synchronized
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "words_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}