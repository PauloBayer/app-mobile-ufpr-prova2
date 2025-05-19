package com.example.masterdetailprova2app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailprova2app.databinding.ItemBookBinding
import com.example.masterdetailprova2app.model.Book

class BookAdapter(
    private var list: List<Book>,
    private val onClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.VH>() {

    inner class VH(val vb: ItemBookBinding) : RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        VH(ItemBookBinding.inflate(LayoutInflater.from(p.context), p, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(h: VH, i: Int) = with(h.vb) {
        val b = list[i]
        tvTitle.text  = b.title
        tvAuthor.text = b.author
        root.setOnClickListener { onClick(b) }
    }

    fun update(newList: List<Book>) { list = newList; notifyDataSetChanged() }
}
