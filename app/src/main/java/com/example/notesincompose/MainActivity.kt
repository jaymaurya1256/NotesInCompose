package com.example.notesincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesincompose.ui.MainViewModel
import com.example.notesincompose.ui.theme.NotesInComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesInComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(){
    val viewModel : MainViewModel = viewModel()
    viewModel.screen
    Notes("Android")
//    NoteDetail()
}

@Composable
fun Notes(name: String) {
    Card(Modifier.padding(25.dp)) {
        Text(text = "Hello $name!")
    }
}

@Composable
fun NoteDetail() {
    Card(Modifier.padding(5.dp)) {
        Column(Modifier.padding(5.dp)) {
            Text(text = "Detail Notes")
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier
                .background(Color.LightGray)
                .height(1.dp)
                .width(100.dp)
            )
            Text(text = "Date and time",
                color = Color(R.color.purple_200),
                fontSize = 8.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@Composable
fun AddNote() {
    Box(modifier = Modifier
        .height(5.dp)
        .width(5.dp)
        .background(Color.Green))
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesInComposeTheme {
//        Notes("Android")
        NoteDetail()
    }
}

