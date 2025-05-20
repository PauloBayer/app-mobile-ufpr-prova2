package com.example.masterdetailprova2app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterdetailprova2app.dao.BookDAO
import com.example.masterdetailprova2app.databinding.ActivityQueryBinding
import android.widget.Toast

class ActivityQuery : AppCompatActivity() {

    private lateinit var vb: ActivityQueryBinding
    private lateinit var dao: BookDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Infla o layout e define o content view
        vb = ActivityQueryBinding.inflate(layoutInflater)
        setContentView(vb.root)

        // Aplica insets no ConstraintLayout de id @id/main
        ViewCompat.setOnApplyWindowInsetsListener(vb.main) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        dao = BookDAO(this)

        vb.buttonSearch.setOnClickListener {
            val query = vb.inputSearch.text.toString().trim()

            if (query.isEmpty()) {
                Toast.makeText(this, "Digite um título para buscar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Busca LIKE (ignora maiúsc./minúsc.)
            val book = dao.all().find { it.title.contains(query, ignoreCase = true) }

            if (book != null) {
                vb.textNameBook.text   = book.title
                vb.textNameAuthor.text = book.author
                vb.textPageNumber.text = book.pages.toString()
            } else {
                Toast.makeText(this, "Livro não encontrado", Toast.LENGTH_SHORT).show()
                vb.textNameBook.text   = ""
                vb.textNameAuthor.text = ""
                vb.textPageNumber.text = ""
            }
        }
    }
}

