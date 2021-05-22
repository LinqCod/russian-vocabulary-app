package com.linqcod.russianvocabulary.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.linqcod.russianvocabulary.MainActivity
import com.linqcod.russianvocabulary.R
import com.linqcod.russianvocabulary.adapters.WordListAdapter
import com.linqcod.russianvocabulary.data.WordViewModel
import kotlinx.android.synthetic.main.fragment_words_page.view.*

class WordsPageFragment : Fragment() {

    private lateinit var mWordViewModel: WordViewModel
    private val adapter = WordListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_words_page, container, false)

        val recyclerview = view.words_list
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        mWordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        mWordViewModel.allWords.observe(viewLifecycleOwner, {
                words ->
            run {
                adapter.setData(words)
            }
        })
        mWordViewModel.randWords.observe(viewLifecycleOwner, {
                words ->adapter.setData(words)
        })
        mWordViewModel.lastWords.observe(viewLifecycleOwner, {
                words ->adapter.setData(words)
        })

        view.addWordBtn.setOnClickListener {
            findNavController().navigate(R.id.action_wordsPageFragment_to_addWordFragment)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.rand_words -> {
                adapter.setData(mWordViewModel.randWords.value ?: emptyList())
            }
            R.id.last_words -> {
                adapter.setData(mWordViewModel.lastWords.value ?: emptyList())
            }
            R.id.all_words -> {
                adapter.setData(mWordViewModel.allWords.value ?: emptyList())
            }
        }
        return super.onOptionsItemSelected(item)
    }
}