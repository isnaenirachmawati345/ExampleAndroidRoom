package com.example.androidroom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(Word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    //WordDao adalah antarmuka; DAO harus berupa antarmuka atau class abstrak.
    //Anotasi @Dao mengidentifikasikannya sebagai class DAO untuk Room.
    //suspend fun insert(word: Word) : Mendeklarasikan fungsi penangguhan untuk menyisipkan satu kata.
    //Anotasi @Insert adalah anotasi metode DAO khusus, sehingga Anda tidak perlu menyediakan SQL apa pun. (Ada pula anotasi @Delete dan @Update untuk menghapus dan memperbarui baris, tetapi tidak digunakan dalam aplikasi ini.)
    //onConflict = OnConflictStrategy.IGNORE: Strategi onConflict yang dipilih akan mengabaikan kata baru jika sama persis dengan kata yang sudah ada dalam daftar. Untuk mengetahui lebih lanjut strategi konflik yang tersedia, lihat dokumentasi.
    //suspend fun deleteAll(): Mendeklarasikan fungsi penangguhan untuk menghapus semua kata.
    //Tidak ada anotasi praktis untuk menghapus beberapa entity, sehingga tindakan tersebut diberi anotasi dengan @Query generik.
    //@Query("DELETE FROM word_table"): @Query mengharuskan Anda menyediakan kueri SQL sebagai parameter string ke anotasi, sehingga memungkinkan kueri baca yang kompleks dan operasi lainnya.
    //fun getAlphabetizedWords(): List<Word>: Metode untuk mendapatkan semua kata dan menghasilkan List Words.
    //@Query("SELECT * FROM word_table ORDER BY word ASC"): Kueri yang menghasilkan daftar kata yang diurutkan dalam urutan naik.
    //Flow menghasilkan nilai satu per satu (bukan sekaligus) yang dapat menghasilkan nilai dari operasi asinkron seperti permintaan jaringan, panggilan database, atau kode asinkron lainnya. Flow mendukung coroutine di seluruh API-nya, sehingga Anda juga dapat mengubah alur menggunakan coroutine.
}