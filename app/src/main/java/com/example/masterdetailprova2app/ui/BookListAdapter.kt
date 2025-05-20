package com.example.masterdetailprova2app.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.masterdetailprova2app.databinding.ItemBookBinding
import com.example.masterdetailprova2app.model.Book

class BookListAdapter(
    ctx: Context,
    private var items: List<Book>
) : ArrayAdapter<Book>(ctx, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ItemBookBinding
        val row = if (convertView == null) {
            ItemBookBinding.inflate(LayoutInflater.from(context), parent, false).also {
                holder = it
                it.root.tag = holder
            }.root
        } else {
            convertView.also { holder = it.tag as ItemBookBinding }
        }

        val book = items[position]
        holder.tvTitle.text  = book.title
        holder.tvAuthor.text = book.author
        // se quiser mudar a capa com base em algo, faça aqui (holder.ivCover)

        return row
    }

    fun update(list: List<Book>) {
        items = list
        clear()
        addAll(list)
    }
}
