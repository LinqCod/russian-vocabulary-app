package com.linqcod.russianvocabulary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.linqcod.russianvocabulary.R
import com.linqcod.russianvocabulary.adapters.WordListAdapter
import com.linqcod.russianvocabulary.data.WordViewModel
import kotlinx.android.synthetic.main.fragment_words_page.view.*

class WordsPageFragment : Fragment() {

    private lateinit var mWordViewNodel: WordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_words_page, container, false)

        val adapter = WordListAdapter()
        val recyclerview = view.words_list
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        mWordViewNodel = ViewModelProvider(this).get(WordViewModel::class.java)
        mWordViewNodel.allWords.observe(viewLifecycleOwner, {
            words -> adapter.setData(words)
        })

        view.addWordBtn.setOnClickListener {
            findNavController().navigate(R.id.action_wordsPageFragment_to_addWordFragment)
        }

        return view
    }

}