package com.ptb.booksearch.libraraysearch.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ptb.booksearch.R
import com.ptb.booksearch.libraraysearch.data.models.Document
import com.ptb.booksearch.libraraysearch.data.models.SearchResults
import com.ptb.booksearch.libraraysearch.presentation.viewmodels.LibrarySearchViewModel
import com.ptb.booksearch.navigation.Screens
import com.ptb.booksearch.network.ApiState
import com.ptb.booksearch.network.Status
import com.ptb.booksearch.ui.theme.BookSearchAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SearchBar(query: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onTextChanged,
        placeholder = { Text("Type Book/Author Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.ten_dp)),
        singleLine = true,
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            errorCursorColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.secondary
        ),
    )
}


@Composable
fun SearchResultList(list: List<Document>, onBookItemClicked : (Document) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(list.size) { index ->
            BooksListItem(list[index],onBookItemClicked)
        }
    }
}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
@Composable
fun BooksListItem(book: Document, onBookItemClicked : (Document) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                MaterialTheme.shapes.medium
            ),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        onClick = {onBookItemClicked(book)}
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(
                    if(book.coverId != null) "http://covers.openlibrary.org/b/id/${book.coverId}-M.jpg"
                    else "https://freesvg.org/img/1488216538.png",
                    builder = {
                        error(R.drawable.broken_link)
                    }),
                contentDescription = book.title,
                modifier = Modifier
                    .size(72.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = book.title ?: "",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    text = book.author?.joinToString(", ") ?: "",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier
                )
            }
        }
    }
}



@Composable
fun HomeScreen(
    query: String,
    state: ApiState<SearchResults>,
    onTextChanged: (String) -> Unit,
    onBookItemClicked : (Document) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Search Book", color = MaterialTheme.colors.secondary) },
            )
        },
        content = {
            @Suppress("Unused") it
            Column(
                Modifier
                    .padding(top = dimensionResource(id = R.dimen.five_dp))
                    .fillMaxSize()
            ) {
                SearchBar(
                    query = query,
                    onTextChanged = { updatedText -> onTextChanged(updatedText) }
                )
                when (state.status) {
                    Status.INIT -> {
                        val message = "Search for your favourite books here."
                        MessageWithIcon(message, R.drawable.search)
                    }
                    Status.LOADING -> {
                        ShowLoader()
                    }
                    Status.SUCCESS -> {
                        if (state.data?.documents?.isNotEmpty() == true) {
                            Text(text = "${state.data?.numFound ?: 0} Results found", modifier = Modifier.padding(start = 16.dp))
                            SearchResultList(state.data.documents, onBookItemClicked)
                        } else {
                            val message =
                                "No books found for your keyword try searching something different."
                            MessageWithIcon(message, R.drawable.sad_face)
                        }
                    }
                    Status.ERROR -> {
                        val message = "Something went wrong at our end. Try again after sometime."
                        MessageWithIcon(message, R.drawable.broken_link)
                    }
                }
            }
        }
    )
}

@Composable
fun MessageWithIcon(message: String, brokenLink: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        ) {
            val modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center)
            Row(modifier = modifier) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = brokenLink),
                    contentDescription = "Error Icon",
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.seventy_two))
                        .width(dimensionResource(id = R.dimen.seventy_two))
                        .padding(
                            bottom = dimensionResource(
                                id = R.dimen.five_dp
                            )
                        ),
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
            Row(modifier = modifier) {
                Text(text = message, textAlign = TextAlign.Center, color = MaterialTheme.colors.secondary)
            }
        }
    }
}

@Composable
fun ShowLoader() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        ) {
            CircularProgressBar()
        }
    }
}

@Composable
fun CircularProgressBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size = 64.dp),
            color = MaterialTheme.colors.secondary,
            strokeWidth = 6.dp
        )
    }
}

@Composable
fun BooksListingScreen(navController: NavController){
    val librarySearchViewModel = hiltViewModel<LibrarySearchViewModel>()
    BookSearchAppTheme(
        content = {
            val state = librarySearchViewModel.searchResultsFlow.collectAsState()
            val queryText = rememberSaveable { mutableStateOf("") }
            val scope = rememberCoroutineScope()
            HomeScreen(queryText.value, state.value, { updatedText ->
                queryText.value = updatedText
                scope.launch {
                    delay(300)
                    librarySearchViewModel.search(queryText.value)
                }
            }, onBookItemClicked = {
                navController.navigate(Screens.BookDetailsScreen.route)
            })
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen("hello", ApiState.init(), onTextChanged = {}, onBookItemClicked = {})
}