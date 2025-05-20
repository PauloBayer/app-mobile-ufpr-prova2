package com.example.masterdetailprova2app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterdetailprova2app.dao.BookDAO
import com.example.masterdetailprova2app.databinding.ActivityQueryBinding

class ActivityQuery : AppCompatActivity() {

    private lateinit var vb: ActivityQueryBinding
    private lateinit var dao: BookDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_query)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vb = ActivityQueryBinding.inflate(layoutInflater)
        setContentView(vb.root)

        dao = BookDAO(this)

        vb.buttonSearch.setOnClickListener {
            val query = vb.inputSearch.text.toString().trim().lowercase()

            if (query.isEmpty()) {
                Toast.makeText(this, "Digite um título para buscar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val book = dao.all().find {
                it.title.trim().lowercase().contains(query)
            }

            if (book != null) {
                vb.textNameBook.text = book.title
                vb.textNameAuthor.text = book.author
                vb.textPageNumber.text = book.pages.toString()
            } else {
                Toast.makeText(this, "Livro não encontrado", Toast.LENGTH_SHORT).show()
                vb.textNameBook.text = ""
                vb.textNameAuthor.text = ""
                vb.textPageNumber.text = ""
            }
        }
    }
}
