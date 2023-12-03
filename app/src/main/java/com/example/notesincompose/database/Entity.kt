package com.example.notesincompose.database

import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "notes_table")
data class Entity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
        )