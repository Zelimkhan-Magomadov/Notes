package zelimkhan.magomadov.notes.ui.core.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

val <T> MutableLiveData<T>.liveData get(): LiveData<T> = this