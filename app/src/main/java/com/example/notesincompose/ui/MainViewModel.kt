package com.example.notesincompose.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.notesincompose.database.AppDatabase
import com.example.notesincompose.database.Entity
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"
class MainViewModel: ViewModel() {

    var note : String = ""
    val screen = mutableStateOf(Screen.Home)
    var notes = mutableStateOf<List<Entity>?>(emptyList())

    fun getNotes(context: Context) {
        val appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app_database"
        ).build()
        try {
            viewModelScope.launch {
                notes.value = appDatabase.dao().getAllNotes().value
            }
        } catch (e: Exception) {
            Log.d(TAG, "getNotes: $e")
        }
    }

    fun addNotes(context: Context, note: Entity) {
        val appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app_database"
        ).build()
        try {
            viewModelScope.launch {
                appDatabase.dao().insertNote(note)
            }
        } catch (e: Exception) {
            Log.d(TAG, "addNotes: $e")
        }

    }

}

enum class Screen {
    Home,
    Detail
}