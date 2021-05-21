package com.linqcod.russianvocabulary.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val word: String,
    //TODO: meanings should be as string's list
    val meanings: String
)
