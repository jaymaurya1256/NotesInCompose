package com.example.notesincompose.ui

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.notesincompose.database.AppDatabase
import com.example.notesincompose.database.Entity

class MainViewModel: ViewModel() {

    var note : String = ""
    val screen = mutableStateOf(Screen.Home)
    var notes = mutableStateOf<LiveData<List<Entity>>>()

    suspend fun getNotes(context: Context) {
        val appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app_database"
        ).build()
        notes.value = appDatabase.dao().getAllNotes()
    }

}

enum class Screen {
    Home,
    Detail
}