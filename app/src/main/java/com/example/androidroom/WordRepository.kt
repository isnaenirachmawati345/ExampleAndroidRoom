import androidx.annotation.WorkerThread
import com.example.androidroom.Word
import com.example.androidroom.WordDao
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}

//DAO diteruskan ke dalam konstruktor repositori, bukan seluruh database. Ini karena repositori hanya memerlukan akses ke DAO, karena DAO berisi semua metode baca/tulis untuk database tersebut. Seluruh database tidak perlu diekspos ke repositori.
//Daftar kata adalah properti publik. Daftar tersebut diinisialisasi dengan mendapatkan daftar Flow kata dari Room; Anda dapat melakukannya karena cara Anda menentukan metode getAlphabetizedWords untuk menampilkan Flow di langkah "Mengamati perubahan database". Room menjalankan semua kueri pada thread terpisah.
//Pengubah suspend memberi tahu compiler bahwa ini perlu dipanggil dari coroutine atau fungsi penangguhan lain.
//Room menjalankan kueri penangguhan dari thread utama.