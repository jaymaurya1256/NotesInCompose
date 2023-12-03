package com.example.notesincompose.ui

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.notesincompose.database.AppDatabase
import com.example.notesincompose.database.Entity

class MainViewModel: ViewModel() {

    var note : String = ""
    val screen = mutableStateOf(Screen.Home)

    suspend fun getNotes(context: Context): List<Entity> {
        val appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app_database"
        ).build()
        return appDatabase.dao().getAllNotes()
    }

}

enum class Screen {
    Home,
    Detail
}