package com.example.masterdetailprova2app.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(ctx: Context) : SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_BOOKS (
                $COL_ID     INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE  TEXT    NOT NULL,
                $COL_AUTHOR TEXT    NOT NULL,
                $COL_PAGES  INTEGER NOT NULL
            );
        """.trimIndent()
        db.execSQL(createTable)

        // 2 livros iniciais exigidos no enunciado
        db.execSQL("INSERT INTO $TABLE_BOOKS ($COL_TITLE,$COL_AUTHOR,$COL_PAGES) VALUES " +
                "('1984','George Orwell',328), " +
                "('Dom Casmurro','Machado de Assis',256)")
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, newV: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKS")
        onCreate(db)
    }

    companion object {
        const val DB_NAME    = "books.db"
        const val DB_VERSION = 1

        const val TABLE_BOOKS = "books"
        const val COL_ID     = "id"
        const val COL_TITLE  = "title"
        const val COL_AUTHOR = "author"
        const val COL_PAGES  = "pages"
    }
}
