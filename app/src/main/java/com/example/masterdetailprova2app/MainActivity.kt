package com.example.masterdetailprova2app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.masterdetailprova2app.dao.BookDAO
import com.example.masterdetailprova2app.databinding.ActivityMainBinding
import com.example.masterdetailprova2app.model.Book
import com.example.masterdetailprova2app.ui.BookListAdapter
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var dao: BookDAO
    private lateinit var adapter: BookListAdapter
    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        dao = BookDAO(this)

        adapter = BookListAdapter(this, dao.all())
        vb.lvBooks.adapter = adapter

        vb.lvBooks.setOnItemClickListener { _, _, pos, _ ->
            val book = adapter.getItem(pos)!!
            startActivity(Intent(this, DetailActivity::class.java)
                .putExtra("id", book.id))
        }

        vb.fabAdd.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java))
        }
        vb.fabSearch.setOnClickListener {
            //startActivity(Intent(this, QueryActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        val list = dao.all()
        adapter.update(list)

        // mostra mensagem se estiver vazia
        vb.emptyGroup.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        vb.lvBooks.visibility    = if (list.isEmpty()) View.GONE  else View.VISIBLE
    }
}