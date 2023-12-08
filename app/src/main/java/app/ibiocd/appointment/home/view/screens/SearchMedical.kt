package app.ibiocd.appointment.presentation.screens.Home

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ibiocd.appointment.home.viewmodel.HomeViewModel
import app.ibiocd.appointment.presentation.normalized.Spacers
import app.ibiocd.appointment.R
import app.ibiocd.appointment.home.SearchWidgetState
import app.ibiocd.appointment.UserViewModel
import app.ibiocd.appointment.model.Favs
import app.ibiocd.appointment.profile.datasource.entity.Medical
import app.ibiocd.appointment.home.view.components.CardFavs
import app.ibiocd.appointment.home.viewmodel.FavViewModel
import app.ibiocd.appointment.presentation.normalized.BlockSpaces
import app.ibiocd.appointment.profile.viewmodel.MedicalViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.runBlocking

@Composable
fun SearchMedical(
    ID: String,
    onBackHome: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    viewModel: UserViewModel = hiltViewModel(),
    favViewModel: FavViewModel = hiltViewModel(),
    medicalViewModel: MedicalViewModel = hiltViewModel(),
) {
    val searchWidgetState by homeViewModel.searchWidgetState
    val searchTextState by homeViewModel.searchTextState

    val isLoading by viewModel.isLoading.observeAsState(false)

    val medicallist by medicalViewModel.medicals.observeAsState(arrayListOf())
    val favs by favViewModel.favs.observeAsState(arrayListOf())
    val settingMap by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = false))
    }
    var LatLngMap by remember { mutableStateOf(LatLng(0.623938, -58.382782)) }

    var int by remember { mutableStateOf(250) }
    var FirstMedical by remember {
        mutableStateOf(
            Medical(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        )
    }
    var FirstFavs by remember { mutableStateOf(Favs("", "", "")) }
    var FirstCard by remember { mutableStateOf(false) }
    var downcard by remember { mutableStateOf(false) }
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLngMap, 11f)
    }
    var medical by rememberSaveable { mutableStateOf(medicallist) }

    var Search by remember { mutableStateOf("") }

    if (Search != "") {
        medical = medicallist.filter {
            it.name_last.contains(
                Search,
                true
            ) && it.tuition.split(":")[1].contentEquals("t")
        }
    } else {
        medical = medicallist.filter { it.tuition.split(":")[1].contentEquals("t") }
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
                    Search = ""

                },
                onSearchClicked = {
                    Search = it
                    Log.d("Searched Text", it)
                },
                onSearchTriggered = {
                    homeViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)

                },
                onBackHome = {
                    onBackHome.invoke()
                }
            )

        })
    {
        Box() {
            Log.d("LATLNG", LatLngMap.toString())
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),

                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true),
                uiSettings = settingMap


            ) {

                Marker(position = LatLngMap)
            }

            Card(
                shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = int.dp)

            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                )
                {


                    var itemCount = medical.size

                    item { BlockSpaces() }
                    items(count = itemCount) { index ->

                        var auxIndex = index;


                        if (itemCount != 0) {

                            val medicals = medical[auxIndex]
                            val favorite =
                                favs.firstOrNull { it.medical == medicals._id && it.patient == ID }
                                    ?: Favs("", "", "")

                            if (index == 0 && FirstCard) {

                                if (favorite._id == "") {
                                    Log.d("FAVORITE", favorite._id.toString())
                                    CardFavs(
                                        isLoading,
                                        FirstMedical,
                                        ID,
                                        FirstFavs,
                                        false,
                                        onClick = {
                                            runBlocking {

                                                favViewModel.getAllFavs()
                                            }
                                            favViewModel.AddFavs(Favs("", FirstMedical._id, ID))

                                        },
                                        onLocation = { latlng, fvr, mdc ->

                                            LatLngMap = latlng
                                            cameraPositionState.move(
                                                update = CameraUpdateFactory.newCameraPosition(
                                                    CameraPosition(LatLngMap, 15f, 0f, 0f)
                                                )
                                            )

                                        })

                                } else {

                                    CardFavs(
                                        isLoading,
                                        FirstMedical,
                                        ID,
                                        FirstFavs,
                                        true,
                                        onClick = {
                                            runBlocking {

                                                favViewModel.getAllFavs()
                                            }
                                            favViewModel.DeleteFavs(FirstFavs)

                                        },
                                        onLocation = { latlng, fvr, mdc ->

                                            LatLngMap = latlng
                                            cameraPositionState.move(
                                                update = CameraUpdateFactory.newCameraPosition(
                                                    CameraPosition(LatLngMap, 15f, 0f, 0f)
                                                )
                                            )

                                        })

                                }



                                BlockSpaces()
                                Divider(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = 30.dp)
                                )
                                Spacers()

                            }


                            if (medicals.direction != "::" && FirstMedical != medicals) {

                                if (favorite._id == "") {
                                    CardFavs(
                                        isLoading,
                                        medicals,
                                        ID,
                                        favorite,
                                        false,
                                        onClick = {
                                            runBlocking {
                                                favViewModel.getAllFavs()

                                            }
                                            favViewModel.AddFavs(Favs("", medicals._id, ID))

                                        },
                                        onLocation = { latlng, fvr, mdc ->

                                            LatLngMap = latlng
                                            FirstCard = true
                                            FirstMedical = mdc
                                            FirstFavs = fvr
                                            cameraPositionState.move(
                                                update = CameraUpdateFactory.newCameraPosition(
                                                    CameraPosition(LatLngMap, 15f, 0f, 0f)
                                                )
                                            )

                                        })

                                } else {
                                    CardFavs(
                                        isLoading,
                                        medicals,
                                        ID,
                                        favorite,
                                        true,
                                        onClick = {
                                            runBlocking {
                                                favViewModel.getAllFavs()

                                            }

                                            favViewModel.DeleteFavs(favorite)

                                        },
                                        onLocation = { latlng, fvr, mdc ->

                                            LatLngMap = latlng
                                            FirstCard = true
                                            FirstMedical = mdc
                                            FirstFavs = fvr
                                            cameraPositionState.move(
                                                update = CameraUpdateFactory.newCameraPosition(
                                                    CameraPosition(LatLngMap, 15f, 0f, 0f)
                                                )
                                            )

                                        })

                                }
                            }


                        }
                    }

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
    onSearchTriggered: () -> Unit,
    onBackHome: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered, onBackHome = {
                    onBackHome.invoke()
                })
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
private fun DefaultAppBar(onSearchClicked: () -> Unit, onBackHome: () -> Unit) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    TopAppBar(
        title = {
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackHome.invoke()
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Black
                )
            }
        },
        elevation = 0.dp,
        backgroundColor = Color.White
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



