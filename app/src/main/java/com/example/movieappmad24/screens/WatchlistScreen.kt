package com.example.movieappmad24.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.AppBottomBar
import com.example.movieappmad24.ui.theme.AppTopBar

fun WatchlistScreen(navController: NavController) {
    MovieApp(navController)
}

@Composable
fun MovieApp(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AppScaffold(navController)
    }
}

@Composable
fun AppScaffold(navController: NavController) {
    Scaffold(
        topBar = { AppTopBar(title = "Watchlist") }, // Убран параметр movieId и передача null
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->
        MovieList(navController, movies = getMovies().subList(0, 2), innerPaddingValues = innerPadding)
    }
}

@Composable
fun MovieList(navController: NavController, movies: List<Movie>, innerPaddingValues: PaddingValues) {
    LazyColumn(contentPadding = innerPaddingValues) {
        items(movies) { movie ->
            // Удален вложенный вызов navigate, теперь он передается в onItemClick
            MovieRow(navController, movie) {
                navController.navigate("detailScreen/${movie.id}")
            }
        }
    }
}

@Composable
fun MovieRow(navController: NavController, movie: Movie, onItemClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var favorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.clickable { onItemClick() } // Вызов onItemClick вместо navigate
            .fillMaxWidth()
            .padding(5.dp),
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter(data = movie.images.first(), builder = {
                        crossfade(true)
                    }),
                    contentDescription = movie.title + " image",
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        modifier = Modifier.clickable { favorite = !favorite },
                        imageVector = if (favorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (favorite) "Remove from Favorites" else "Add to Favorites"
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Icon(
                    modifier = Modifier.clickable { expanded = !expanded },
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }
        }
        AnimatedVisibility(visible = expanded) {
            ToggleMovieDetails(movie = movie)
        }
    }
}

@Composable
fun ToggleMovieDetails(movie: Movie) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Director: " + movie.director)
        Text(text = "Released: " + movie.year)
        Text(text = "Genre: " + movie.genre)
        Text(text = "Actors: " + movie.actors)
        Text(text = "Rating: " + movie.rating)
        Divider(modifier = Modifier.padding(10.dp))
        Text(text = "Plot: " + movie.plot)
    }
}
