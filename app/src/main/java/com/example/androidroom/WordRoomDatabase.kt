package com.example.androidroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.chrono.HijrahChronology.INSTANCE

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()

                    // Delete all content here.
                    wordDao.deleteAll()

                    // Add sample words.
                    var word = Word("Hello")
                    wordDao.insert(word)
                    word = Word("World!")
                    wordDao.insert(word)

                    // TODO: Add your own words!
                    word = Word("TODO!")
                    wordDao.insert(word)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

// Annotates class to be a Room Database with a table (entity) of the Word class
//Room adalah lapisan database di atas database SQLite.
//Room menangani tugas biasa yang Anda gunakan untuk ditangani dengan SQLiteOpenHelper.
//Room menggunakan DAO untuk mengeluarkan kueri ke database-nya.
//Secara default, untuk menghindari performa UI yang buruk, Room tidak mengizinkan Anda untuk mengeluarkan kueri di thread utama. Saat Kueri Room menghasilkan Flow, kueri akan otomatis berjalan secara asinkron di thread latar belakang.
//Room menyediakan pemeriksaan waktu kompilasi terhadap pernyataan SQLite.
//detail code diatas :
//Class database untuk Room harus abstract dan memperluas RoomDatabase.
//Anda menganotasi class menjadi database Room dengan @Database dan menggunakan parameter anotasi untuk mendeklarasikan entity yang termasuk dalam database dan menetapkan nomor versi. Setiap entity sesuai dengan tabel yang akan dibuat dalam database. Migrasi database tidak termasuk dalam cakupan codelab ini. Jadi di sini, exportSchema telah disetel ke salah untuk menghindari peringatan build. Di aplikasi yang sebenarnya, coba setel direktori untuk Room yang akan digunakan untuk mengekspor skema agar Anda dapat memeriksa skema saat ini ke dalam sistem kontrol versi.
//Database mengekspos DAO melalui metode "getter" abstrak untuk setiap @Dao.
//Anda menentukan singleton, yakni WordRoomDatabase, untuk mencegah beberapa instance database dibuka secara bersamaan.
//getDatabase akan menghasilkan singleton. Ini akan membuat database saat pertama kali diakses, menggunakan builder database Room untuk membuat objek RoomDatabase dalam konteks aplikasi dari class WordRoomDatabase dan menamainya "word_database".