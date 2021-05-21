package com.linqcod.russianvocabulary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.linqcod.russianvocabulary.MainActivity
import com.linqcod.russianvocabulary.R
import com.linqcod.russianvocabulary.data.Word
import com.linqcod.russianvocabulary.data.WordViewModel
import kotlinx.android.synthetic.main.fragment_add_word.*
import kotlinx.android.synthetic.main.fragment_add_word.view.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException

class AddWordFragment : Fragment() {

    private lateinit var mWordViewModel: WordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_word, container, false)

        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        view.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null && query.isNotBlank()) {
                    val word = query.trim().lowercase()
                    findWordFromSlovaOnline(word)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        view.addWordBtn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun findWordFromRusTXT(word: String) {
        val tempWord = word.replace(" ", "").lowercase()
        Thread {
            val stringBuilder = StringBuilder()
            try {
                val doc: Document =
                    Jsoup.connect("https://rustxt.ru/dict/$tempWord")
                        .get()
                val meanings: Elements = doc.select(".answer")
                for (meaning in meanings) {
                    //TODO: parse author of the meaning
                    Log.d("TEST", "2")
                    meaning.getElementsByClass("mb-1").forEach {
                        stringBuilder.append(it.text()).append("\n")
                    }
                }
            } catch (e: IOException) {
                (context as MainActivity).runOnUiThread {
                    Toast.makeText(requireContext(), "Error while searching word", Toast.LENGTH_SHORT).show()
                }
            }
            (context as MainActivity).runOnUiThread {
                wordET.setText(tempWord)
                meaningsET.setText(stringBuilder.toString())
            }
        }.start()
    }

    private fun findWordFromSlovaOnline(word: String) {
        val urls = listOf("https://efremova.slovaronline.com/", "https://dal.slovaronline.com/")
        val defaultUrl = "https://slovaronline.com/"
        var i = 0
        var meaning = ""

        Thread {
            while(meaning.isBlank() && i < urls.size) {
                meaning = findWord(urls[i++], word, "highlight")
            }
            if(meaning.isBlank()) {
                meaning = findWord(defaultUrl, word, "search-result")
            }
            (context as MainActivity).runOnUiThread {
                wordET.setText(word)
                meaningsET.setText(meaning)
            }
        }.start()
    }

    private fun findWord(url: String, word: String, c: String): String {
        var meaning = ""

        try {
            val doc: Document =
                Jsoup.connect("${url}search?s=$word")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
                    .get()
            val result: Element? = doc.getElementsByClass(c).firstOrNull()
            meaning = result?.getElementsByClass("truncate")?.text() ?: ""
        } catch (e: IOException) {
            Log.e("Word", e.toString())
        }

        return meaning
    }

    private fun insertDataToDatabase() {
        val tempWord = wordET.text.toString()
        val meanings = meaningsET.text.toString()

        if(inputCheck(tempWord, meanings)) {
            val word = Word(0, tempWord, meanings)
            mWordViewModel.addWord(word)
            Toast.makeText(requireContext(), "Word successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addWordFragment_to_wordsPageFragment)
        }
        else {
            Toast.makeText(requireContext(), "Please, fill out all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(word: String, meanings: String): Boolean {
        return word.isNotBlank() && meanings.isNotBlank()
    }
}