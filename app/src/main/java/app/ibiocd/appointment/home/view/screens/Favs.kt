package com.nopalsoft.simple.rest.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.home.view.components.CardFavs
import app.ibiocd.appointment.home.view.components.LoadingCardFavs
import app.ibiocd.appointment.home.viewmodel.HomeViewModel
import app.ibiocd.appointment.home.SearchWidgetState
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.home.viewmodel.FavViewModel
import app.ibiocd.appointment.presentation.normalized.BlockSpace
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun Favs(
    ID: String,
    homeViewModel: HomeViewModel= hiltViewModel(),
    viewModel: UserViewModel = hiltViewModel(),
    favViewModel: FavViewModel = hiltViewModel(),
    medicalViewModel: MedicalViewModel = hiltViewModel()
) {
    val searchWidgetState by homeViewModel.searchWidgetState
    val searchTextState by homeViewModel.searchTextState
    val context = LocalContext.current
    val medicallist by medicalViewModel.medicals.observeAsState(arrayListOf())
    val favs by favViewModel.favs.observeAsState(arrayListOf())
    val isLoading by viewModel.isLoading.observeAsState(false)
    var medical by rememberSaveable { mutableStateOf(medicallist) }

    var Search by remember { mutableStateOf("") }

    if (Search != "") {
        medical = medicallist.filter { it.name_last.contains(Search, true) }
    } else {
        medical = medicallist
    }

    Scaffold(
        topBar = {
            HomeAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    Search = it
                    homeViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    homeViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Search = it
                    Log.d("Searched Text", it)
                },
                onSearchTriggered = {
                    homeViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )

        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .background(Color.White)
        )
        {


            item {
                BlockSpace()

            }

            var itemCount = medical.size
            if (isLoading) itemCount++

            items(count = itemCount) { index ->
                var auxIndex = index;
                if (isLoading) {
                    if (auxIndex == 0)
                        return@items LoadingCardFavs()
                    auxIndex--
                }
                if (itemCount != 0) {

                    val medicals = medical[auxIndex]


                    val favorite = favs.find { it.medical == medicals._id && it.patient == ID }
                        ?: app.ibiocd.appointment.model.Favs(
                            "",
                            "",
                            ""
                        )


                    if (favorite._id != "") {
                        CardFavs(isLoading, medicals, ID, favorite, true, onClick = {
                            favViewModel.DeleteFavs(favorite)
                            runBlocking {
                                favViewModel.getAllFavs()
                            }

                        }, onLocation = { latlng, fvr, mdc -> })

                    }

                    //   CardFavs(medicals,ID,,true)


                }
            }


        }


    }
}

@Composable
private fun HomeAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
private fun DefaultAppBar(onSearchClicked: () -> Unit) {

    TopAppBar(
        title = { },
        backgroundColor = Color.White,
        elevation = 0.dp,
        actions = {
            IconButton(onClick = {
                onSearchClicked()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Black
                )
            }
        }
    )
}

@Composable
private fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = 0.dp,
        color = Color.White
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.Black
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = { onSearchClicked(text) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.Black
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black.copy(alpha = ContentAlpha.medium)
            ))
    }
}
