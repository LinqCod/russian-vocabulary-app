package com.linqcod.russianvocabulary.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application): AndroidViewModel(application) {

    val allWords: LiveData<List<Word>>
    val randWords: LiveData<List<Word>>
    val lastWords: LiveData<List<Word>>
    private val repository: WordRepository

    init {
        val wordDao = WordDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordDao)
        allWords = repository.allWords
        randWords = repository.randWords
        lastWords = repository.lastWords
    }

    fun addWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWord(word)
        }
    }
}