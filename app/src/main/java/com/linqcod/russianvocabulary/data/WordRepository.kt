package com.linqcod.russianvocabulary.data

import androidx.lifecycle.LiveData

class WordRepository(private val wordDao: WordDao) {

    val allWords = wordDao.getAllWords()

    suspend fun addWord(word: Word) {
        wordDao.addWord(word)
    }

    fun get10RandWords(): List<Word> {
        return wordDao.get10RandomWords()
    }

    fun get10LastWords(): List<Word> {
        return wordDao.get10LastWords()
    }
}