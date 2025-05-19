package com.example.masterdetailprova2app.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.masterdetailprova2app.model.Book
import com.example.masterdetailprova2app.util.ioThread

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(ctx: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(ctx).also { INSTANCE = it }
            }

        private fun build(ctx: Context) =
            Room.databaseBuilder(ctx, AppDatabase::class.java, "books.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            get(ctx).bookDao().apply {
                                insert(Book(title = "1984", author = "George Orwell", pages = 328))
                                insert(Book(title = "Dom Casmurro", author = "Machado de Assis", pages = 256))
                            }
                        }
                    }
                })
                .build()
    }
}