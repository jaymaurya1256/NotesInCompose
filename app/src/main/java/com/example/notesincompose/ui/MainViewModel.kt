package com.example.notesincompose.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val screen = mutableStateOf(Screen.Home)

}

enum class Screen {
    Home,
    Detail
}