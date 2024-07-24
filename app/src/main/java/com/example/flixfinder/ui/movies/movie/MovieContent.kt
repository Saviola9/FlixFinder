package com.example.flixfinder.ui.movies.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.flixfinder.R
import com.example.flixfinder.util.rateColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieContent(movie: Movie, modifier: Modifier = Modifier, onMovieClicked : (Int) -> Unit = {}) {


    Card(onClick = { onMovieClicked(movie.id)},
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        ) {

        Box {
            MoviePoster(movie.posterPath, movie.name)

            MovieInfo(
                movie,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color(0x97000000))
                )
            MovieRate(
                movie.voteAverage,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(2f)
                    .offset(4.dp)
            )
        }

    }
}

@Composable
fun MovieRate(rate: Double, modifier: Modifier) {

    val colors = Color.rateColors(movieRate = rate)
    val brush = remember(rate) { Brush.horizontalGradient(colors)}
    Text(
        text = rate.toString(),
        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
        modifier = modifier
            .background(brush, RoundedCornerShape(50))
            .padding(horizontal = 10.dp)
            .shadow(8.dp)
    )
}

@Composable
fun MovieInfo(movie : Movie, modifier: Modifier) {

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        MovieName(movie.name)
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            MovieFeature(Icons.Default.DateRange, movie.releaseDate)
            MovieFeature(icon = Icons.Default.ThumbUp, field = movie.voteCount.toString())
        }
    }
}

@Composable
fun MovieFeature(icon: ImageVector, field: String) {

    Row(verticalAlignment = Alignment.CenterVertically)
    {

        Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp) )
        Text(
            text = field,
            style = MaterialTheme.typography.labelLarge.copy(
                color = Color.White,
                letterSpacing = 1.5.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W400
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
    }
}

@Composable
fun MovieName(name: String) = Text(
    text = name,
    style = MaterialTheme.typography.titleMedium.copy(
        color = Color.White,
        letterSpacing = 1.5.sp,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.W500
    ),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis
)

@Composable
private fun BoxScope.MoviePoster(posterPath : String, movieName : String) {

    val painter = rememberAsyncImagePainter(
        model = posterPath,
        error = rememberVectorPainter(image = Icons.Default.BrokenImage),
        placeholder = rememberVectorPainter(image = Icons.Default.Movie)
        )

    val colorFilter = when (painter.state) {
        is AsyncImagePainter.State.Loading, is AsyncImagePainter.State.Error -> ColorFilter.tint(MaterialTheme.colorScheme.surfaceTint)
        else -> null
    }
    
    val scale = if(painter.state is AsyncImagePainter.State.Success) ContentScale.FillBounds else ContentScale.Fit
    
    Image(painter = painter, 
        colorFilter = colorFilter,
        contentScale = scale,
        contentDescription = stringResource(id = R.string.movie_poster_content_description, movieName),
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center)
    )

}

@Preview
@Composable
private fun MovieP() {
   
    val fakeMovie = Movie(1337, "3 Idiots", "01.03.1337", "Poster", 9.24, 1337)
    MovieContent(movie = fakeMovie)
}
