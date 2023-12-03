package com.example.notesincompose.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@androidx.room.Dao
interface Dao {
    @Query("SELECT * FROM notes_table")
    suspend fun getAllNotes(): List<Entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Entity)

    @Delete
    suspend fun deleteNote(note: Entity)
}
