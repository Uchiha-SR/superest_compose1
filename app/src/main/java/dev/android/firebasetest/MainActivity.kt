package dev.android.firebasetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import dev.android.firebasetest.ui.theme.FirebasetestTheme
import dev.android.firebasetest.ui.theme.gray_fade

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContent {
            FirebasetestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Column {
                        TopAppBar(
                            title = {
                                Text(text = "Food Categories")
                            },
                            backgroundColor = Color.Black,
                            contentColor = Color.White

                        )

                        SetData(viewModel)

                    }
                }
            }
        }
    }
}

        @Composable
        private fun SetData(viewModel: MainViewModel) {
            when (val result = viewModel.response.value) {
                is DataState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is DataState.Success -> {
                    ShowLazyList(result.data)
                }
                is DataState.Failure -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = result.message,
                            fontSize = MaterialTheme.typography.h5.fontSize,
                        )
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error Fetching data",
                            fontSize = MaterialTheme.typography.h5.fontSize,
                        )
                    }
                }
            }
        }

        @Composable
        fun ShowLazyList(foods: MutableList<Products>) {
            LazyColumn {
                items(foods) { food ->
                    CardItem(food)
                }
            }
        }

        @Composable
        fun CardItem(food: Products) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(10.dp)
            ) {

                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = rememberImagePainter(food.filename),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "My content description",
                        contentScale = ContentScale.FillWidth
                    )

                    Text(
                        text = food.title!!,
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(gray_fade),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }

            }
        }
