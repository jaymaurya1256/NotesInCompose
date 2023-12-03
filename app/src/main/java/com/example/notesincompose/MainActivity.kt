package com.example.notesincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.notesincompose.database.AppDatabase
import com.example.notesincompose.database.Entity
import com.example.notesincompose.ui.MainViewModel
import com.example.notesincompose.ui.Screen
import com.example.notesincompose.ui.theme.NotesInComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var notes: List<Entity>? = null
        setContent {
            NotesInComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: MainViewModel = viewModel()
                    lifecycleScope.launch {
                        notes = viewModel.getNotes(applicationContext)
                    }
                    LoadingScreen()
                    notes?.let {
                        App(viewModel, it)
                        AddNote()
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Content goes here

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun App(viewModel: MainViewModel, notes: List<Entity>) {
    if (viewModel.screen.value == Screen.Home) {
        Notes("Android", viewModel)
    } else {
        NoteDetail(viewModel.note)
    }
}

@Composable
fun Notes(note: String, viewModel: MainViewModel) {
    Card(
        Modifier
            .padding(25.dp)
            .clickable {
                viewModel.note = note
                viewModel.screen.value = Screen.Detail
            }
    ) {
        Text(text = note)
    }
}

@Composable
fun NoteDetail(note: String) {
    Card(Modifier.padding(5.dp)) {
        Column(Modifier.padding(5.dp)) {
            Text(text = note)
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .height(1.dp)
                    .width(100.dp)
            )
            Text(
                text = "Date and time",
                color = Color(R.color.purple_200),
                fontSize = 8.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@Composable
fun AddNote() {
    Box(
        modifier = Modifier
            .height(5.dp)
            .width(5.dp)
            .background(Color.Green) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesInComposeTheme {
//        Notes("Android")
//        NoteDetail()
    }
}

