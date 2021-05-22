package com.linqcod.russianvocabulary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(word: Word)

    @Query("SELECT * FROM words_table ORDER BY RANDOM() LIMIT 10")
    fun get10RandomWords(): LiveData<List<Word>>

    @Query("SELECT * FROM words_table ORDER BY id ASC LIMIT 10")
    fun get10LastWords(): LiveData<List<Word>>

    @Query("SELECT * FROM words_table ORDER BY id ASC")
    fun getAllWords(): LiveData<List<Word>>

}