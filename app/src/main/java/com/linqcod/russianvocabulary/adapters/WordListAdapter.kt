package com.linqcod.russianvocabulary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linqcod.russianvocabulary.R
import com.linqcod.russianvocabulary.data.Word
import kotlinx.android.synthetic.main.word_row_layout.view.*

class WordListAdapter: RecyclerView.Adapter<WordListAdapter.MyViewHolder>() {

    private var words = emptyList<Word>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.word_row_layout, parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = words[position]
        holder.itemView.wordTV.text = currentItem.word
        holder.itemView.meaningsTV.text = currentItem.meanings
    }

    override fun getItemCount(): Int {
        return words.size
    }

    fun setData(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

}