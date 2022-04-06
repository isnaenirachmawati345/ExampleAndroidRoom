package com.example.androidroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    // bisa juga pakai cara biasa :
    // `@PrimaryKey(autoGenerate = true) val id: Int,`
    //`@ColumnInfo(name = "word") val word: String`
    @PrimaryKey @ColumnInfo(name = "word") val word: String)

//@Entity(tableName = "word_table") Setiap class @Entity mewakili tabel SQLite.
    // Anotasikan deklarasi class untuk menunjukkan bahwa itu adalah entity.
    // Anda dapat menentukan nama tabel jika ingin namanya berbeda dari nama class.
    // Anotasi ini menamai tabel sebagai "word_table".
//@PrimaryKey Setiap entity memerlukan kunci utama. Sederhananya,
    // setiap kata berfungsi sebagai kunci utamanya sendiri.
//@ColumnInfo(name = "word") Menentukan nama kolom dalam tabel jika Anda ingin namanya
    // berbeda dari nama variabel anggota. Anotasi ini menamai kolom sebagai "word".
