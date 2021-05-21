package com.linqcod.russianvocabulary.data

class WordRepository(private val wordDao: WordDao) {

    val allWords = wordDao.getAllWords()

    suspend fun addWord(word: Word) {
        wordDao.addWord(word)
    }

}