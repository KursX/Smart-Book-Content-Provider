package com.kursx.smartbook.books

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

const val BOOKS_URI = "content://com.kursx.smartbook/books"

class MainActivity : ComponentActivity() {

    private var books by mutableStateOf<List<Book>>(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        observeInitBooks()

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    books.forEach { book ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(100.dp)
                                .clickable {
                                    startActivity(Intent(Intent.ACTION_VIEW, book.deeplink))
                                },
                        ) {
                            AsyncImage(
                                model = book.cover,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .aspectRatio(1f),
                            )
                            Text(
                                text = book.name,
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeInitBooks() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                contentResolver.query(BOOKS_URI.toUri(), null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val languageIndex = cursor.getColumnIndex("language").takeIf { it != -1 } ?: return@use
                        val nameIndex = cursor.getColumnIndex("name").takeIf { it != -1 } ?: return@use
                        val authorIndex = cursor.getColumnIndex("author").takeIf { it != -1 } ?: return@use
                        val coverIndex = cursor.getColumnIndex("cover").takeIf { it != -1 } ?: return@use
                        val deeplinkIndex = cursor.getColumnIndex("deeplink").takeIf { it != -1 } ?: return@use

                        val list = mutableListOf<Book>()
                        do {
                            val book = Book(
                                language = cursor.getString(languageIndex),
                                name = cursor.getString(nameIndex),
                                author = cursor.getString(authorIndex),
                                cover = cursor.getString(coverIndex).toUri(),
                                deeplink = cursor.getString(deeplinkIndex).toUri(),
                            )
                            list.add(book)
                        } while (cursor.moveToNext())

                        books = list
                    }
                }
            }
        }
    }
}

class Book(
    val language: String,
    val name: String,
    val author: String,
    val cover: Uri,
    val deeplink: Uri,
)