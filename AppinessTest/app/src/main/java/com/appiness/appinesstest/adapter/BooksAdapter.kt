package com.appiness.appinesstest.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appiness.appinesstest.R
import com.appiness.appinesstest.model.BooksModel
import com.appiness.appinesstest.utils.inflate
import kotlinx.android.synthetic.main.adapter_books.view.*

class BooksAdapter(val context: Context) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    private var mBooks: ArrayList<BooksModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.adapter_books))
    }

    override fun getItemCount(): Int {
        return mBooks?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mBooks!![position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(book: BooksModel) {
            itemView.tvTitle.text = book.title
            itemView.tvNumBackers.text = book.numBackers
            itemView.tvBy.text = book.by
        }
    }

    fun setBooks(books: ArrayList<BooksModel>) {
        mBooks = books
        notifyDataSetChanged()
    }

}