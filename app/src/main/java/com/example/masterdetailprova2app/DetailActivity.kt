package com.example.masterdetailprova2app

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterdetailprova2app.dao.BookDAO
import com.example.masterdetailprova2app.databinding.ActivityDetailBinding
import com.example.masterdetailprova2app.model.Book

class DetailActivity : AppCompatActivity() {

    private lateinit var dao: BookDAO
    private var bookId: Int? = null
    private lateinit var vb: ActivityDetailBinding

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        vb  = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(vb.root)
        dao = BookDAO(this)

        bookId = intent.getIntExtra("id", -1).takeIf { it != -1 }
        bookId?.let { loadBook(it) }

        vb.btnSave.setOnClickListener { saveBook() }
        vb.btnDelete.setOnClickListener { deleteBook() }
        vb.btnDelete.visibility = if (bookId == null) View.GONE else View.VISIBLE
    }

    private fun loadBook(id: Int) {
        val book = dao.all().find { it.id == id } ?: return
        vb.etTitle.setText(book.title)
        vb.etAuthor.setText(book.author)
        vb.etPages.setText(book.pages.toString())
    }

    private fun saveBook() {
        val book = Book(
            id     = bookId ?: 0,
            title  = vb.etTitle.text.toString(),
            author = vb.etAuthor.text.toString(),
            pages  = vb.etPages.text.toString().toIntOrNull() ?: 0
        )
        if (bookId == null) dao.insert(book) else dao.update(book)
        finish()
    }

    private fun deleteBook() {
        bookId?.let { dao.delete(Book(id = it)) }
        finish()
    }
}
