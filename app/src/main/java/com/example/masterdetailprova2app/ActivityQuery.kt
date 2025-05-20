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

        // Uma ÚNICA vez setContentView
        vb = ActivityQueryBinding.inflate(layoutInflater)
        setContentView(vb.root)

        val base = resources.getDimensionPixelSize(R.dimen.base_padding)

        // Ajuste de insets opcional
        ViewCompat.setOnApplyWindowInsetsListener(vb.main) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                bars.left   + base,
                bars.top    + base,
                bars.right  + base,
                bars.bottom + base
            )
            insets
        }

        dao = BookDAO(this)

        vb.buttonSearch.setOnClickListener {
            val query = vb.inputSearch.text.toString().trim()

            if (query.isBlank()) {
                Toast.makeText(this, "Digite um título para buscar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ❶ pegue TODOS que contêm o termo
            val list = dao.all().filter {
                it.title.contains(query, ignoreCase = true)
            }

            if (list.isEmpty()) {
                Toast.makeText(this, "Nenhum livro encontrado", Toast.LENGTH_SHORT).show()
                vb.tvResults.text = ""
                return@setOnClickListener
            }

            // ❷ monta string bonitinha (3 infos de cada livro)
            val info = buildString {
                list.forEach { b ->
                    append("• ${b.title}\n")
                    append("  Autor: ${b.author}\n")
                    append("  Páginas: ${b.pages}\n\n")
                }
            }

            vb.tvResults.text = info.trimEnd()
        }
    }
}

