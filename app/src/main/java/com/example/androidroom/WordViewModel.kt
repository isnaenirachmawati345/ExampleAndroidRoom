package com.example.androidroom

import WordRepository
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//membuat class WordViewModel yang mendapatkan WordRepository sebagai parameter dan memperluas ViewModel. Repositori adalah satu-satunya dependensi yang diperlukan ViewModel. Jika mungkin diperlukan, class lain juga akan diteruskan dalam konstruktor.
//menambahkan variabel anggota LiveData publik untuk menyimpan daftar kata ke cache.
//melakukan inisialisasi LiveData dengan Flow allWords dari Repositori. Anda kemudian mengonversi Flow ke LiveData dengan memanggil asLiveData().
//membuat metode insert() wrapper yang memanggil metode insert() Repositori. Dengan begitu, implementasi insert() dienkapsulasi dari UI. Kita meluncurkan coroutine baru dan memanggil penyisipan repositori, yang merupakan fungsi penangguhan. Seperti yang disebutkan, ViewModels memiliki cakupan coroutine berdasarkan siklus prosesnya yang disebut viewModelScope, yang akan Anda gunakan di sini.
//membuat ViewModel dan mengimplementasikan ViewModelProvider.Factory yang mendapatkan dependensi yang diperlukan sebagai parameter untuk membuat WordViewModel: WordRepository.