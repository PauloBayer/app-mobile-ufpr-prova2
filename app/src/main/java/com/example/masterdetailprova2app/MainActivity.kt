package com.example.masterdetailprova2app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.masterdetailprova2app.dao.BookDAO
import com.example.masterdetailprova2app.databinding.ActivityMainBinding
import com.example.masterdetailprova2app.model.Book

class MainActivity : AppCompatActivity() {
    private lateinit var dao: BookDAO
    private lateinit var adapter: ArrayAdapter<Book>
    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        dao = BookDAO(this)

        // 1) Cria o ArrayAdapter mostrando o título do Book (toString() retorna título)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            dao.all()
        )

        // 2) Set no ListView
        vb.lvBooks.adapter = adapter

        // 3) Clique no item → abre DetailActivity com o ID do livro
        vb.lvBooks.setOnItemClickListener { _, _, position, _ ->
            val book = adapter.getItem(position)!!
            startActivity(
                Intent(this, DetailActivity::class.java)
                    .putExtra("id", book.id)
            )
        }

        // 4) Clique no FAB → novo livro
        vb.fabAdd.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Atualiza a lista sempre que voltar à tela
        adapter.clear()
        adapter.addAll(dao.all())
    }
}
