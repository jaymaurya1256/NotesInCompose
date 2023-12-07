package com.example.notesincompose

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

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
                    LaunchedEffect(true) {
                        lifecycleScope.launch {
                            viewModel.getNotes(applicationContext)
                        }
                    }
                    LoadingScreen()
                    App(applicationContext,viewModel, viewModel.notes.value)
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(250.dp)
    ) {
        // Content goes here

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun App(context: Context, viewModel: MainViewModel, notes: List<Entity>?) {
    if (viewModel.screen.value == Screen.Home) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
        ) {
            if(notes?.isNotEmpty() == true) {
                Column(Modifier.padding(5.dp)) {
                    for (i in notes) {
                        Notes(i.name, viewModel)
                    }
                }
            }
            Box(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
            ) {
                AddNote(context, viewModel)
            }
        }
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
fun AddNote(context: Context, viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .height(5.dp)
            .width(5.dp)
            .background(Color.Green)
    ) {
        Row(
            Modifier.padding(16.dp)
        ) {
            var text by remember { mutableStateOf("") }
            TextField(value = text, onValueChange = { text = it }, label = { Text("Add a  Note")})
            Spacer(modifier = Modifier.width(25.dp))
            Button(
                onClick = {
                    viewModel.addNotes(context, Entity(name = text))
                },
                modifier = Modifier
                    .padding(16.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesInComposeTheme {
//        Notes("Android")
//        NoteDetail()
    }
}

