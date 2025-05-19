package com.example.masterdetailprova2app.data

import androidx.room.*
import com.example.masterdetailprova2app.model.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY title")
    suspend fun all(): List<Book>

    @Query("SELECT * FROM books WHERE title LIKE :q ORDER BY title")
    suspend fun searchByTitle(q: String): List<Book>

    @Insert
    suspend fun insert(book: Book): Long

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)
}