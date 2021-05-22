package com.linqcod.russianvocabulary.data

import androidx.lifecycle.LiveData

class WordRepository(private val wordDao: WordDao) {

    val allWords = wordDao.getAllWords()
    val randWords = wordDao.get10RandomWords()
    val lastWords = wordDao.get10LastWords()

    suspend fun addWord(word: Word) {
        wordDao.addWord(word)
    }

}