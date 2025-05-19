package com.example.masterdetailprova2app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterdetailprova2app.dao.BookDAO
import com.example.masterdetailprova2app.model.Book

class MainActivity : AppCompatActivity() {
    private lateinit var dao: BookDAO
    private lateinit var adapter: ArrayAdapter<Book>

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        val vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        dao = BookDAO(this)

        adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            dao.all()
        )
        vb.lvBooks.adapter = adapter

        /**vb.lvBooks.setOnItemClickListener { _, _, pos, _ ->
            val book = adapter.getItem(pos)!!
            startActivity(
                Intent(this, DetailActivity::class.java)
                .putExtra("id", book.id))
        }

        vb.fabAdd.setOnClickListener {
            //startActivity(Intent(this, DetailActivity::class.java))
        }
        vb.btnSearch.setOnClickListener {
            //startActivity(Intent(this, QueryActivity::class.java))
        } **/
    }

    override fun onResume() {
        super.onResume()
        adapter.clear()
        adapter.addAll(dao.all())
    }
}