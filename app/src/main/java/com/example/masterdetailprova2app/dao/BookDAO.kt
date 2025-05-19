package com.example.masterdetailprova2app.dao

import android.content.ContentValues
import android.content.Context
import com.example.masterdetailprova2app.db.DBHelper
import com.example.masterdetailprova2app.model.Book

class BookDAO(ctx: Context) {
    private val helper = DBHelper(ctx)

    fun insert(book: Book): Long {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(DBHelper.COL_TITLE,  book.title)
            put(DBHelper.COL_AUTHOR, book.author)
            put(DBHelper.COL_PAGES,  book.pages)
        }
        return db.insert(DBHelper.TABLE_BOOKS, null, cv).also { db.close() }
    }

    fun all(): List<Book> {
        val list = mutableListOf<Book>()
        val db   = helper.readableDatabase
        val c = db.query(DBHelper.TABLE_BOOKS, null, null, null, null, null,
            "${DBHelper.COL_TITLE} ASC")
        while (c.moveToNext()) {
            list.add(
                Book(
                    id     = c.getInt(c.getColumnIndexOrThrow(DBHelper.COL_ID)),
                    title  = c.getString(c.getColumnIndexOrThrow(DBHelper.COL_TITLE)),
                    author = c.getString(c.getColumnIndexOrThrow(DBHelper.COL_AUTHOR)),
                    pages  = c.getInt(c.getColumnIndexOrThrow(DBHelper.COL_PAGES))
                )
            )
        }
        c.close(); db.close()
        return list
    }

    fun searchByTitle(term: String): List<Book> {
        val list = mutableListOf<Book>()
        val db   = helper.readableDatabase
        val c = db.query(
            DBHelper.TABLE_BOOKS,
            null,
            "${DBHelper.COL_TITLE} LIKE ?",
            arrayOf("%$term%"),
            null, null,
            "${DBHelper.COL_TITLE} ASC"
        )
        while (c.moveToNext()) {
            list.add(
                Book(
                    id     = c.getInt(c.getColumnIndexOrThrow(DBHelper.COL_ID)),
                    title  = c.getString(c.getColumnIndexOrThrow(DBHelper.COL_TITLE)),
                    author = c.getString(c.getColumnIndexOrThrow(DBHelper.COL_AUTHOR)),
                    pages  = c.getInt(c.getColumnIndexOrThrow(DBHelper.COL_PAGES))
                )
            )
        }
        c.close(); db.close()
        return list
    }

    fun update(book: Book): Int {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(DBHelper.COL_TITLE,  book.title)
            put(DBHelper.COL_AUTHOR, book.author)
            put(DBHelper.COL_PAGES,  book.pages)
        }
        return db.update(
            DBHelper.TABLE_BOOKS, cv,
            "${DBHelper.COL_ID} = ?", arrayOf(book.id.toString())
        ).also { db.close() }
    }

    fun delete(book: Book): Int =
        helper.writableDatabase.use { db ->
            db.delete(DBHelper.TABLE_BOOKS,
                "${DBHelper.COL_ID} = ?", arrayOf(book.id.toString()))
        }
}
