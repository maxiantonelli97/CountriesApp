package com.antonelli.countriesapp.ui.main

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.antonelli.countriesapp.R
import com.antonelli.countriesapp.entity.CountryModel
import com.antonelli.countriesapp.enums.StatesEnum
import com.antonelli.countriesapp.navigation.AppScreens
import com.antonelli.countriesapp.ui.loading.loadingScreen

@Composable
fun mainScreen(
    navController: NavController,
    homeViewModel: MainViewModel = hiltViewModel(),
) {
    val responseEnum by homeViewModel.responseEnum.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val searchResult by homeViewModel.response.collectAsStateWithLifecycle()

    if (isLoading == true) {
        loadingScreen()
    }

    if (responseEnum != null) {
        when (responseEnum!!) {
            StatesEnum.START -> {
                homeViewModel.getAllCountries()
            }
            StatesEnum.SUCCESS -> {
                homeConstraint(
                    list = searchResult,
                    searchText = homeViewModel.searchQuery,
                    onSearchQueryChange = { homeViewModel.setSearchText(it)},
                    onSearch = {},
                    navController = navController
                )
            }
            StatesEnum.ERROR -> {
                navController.navigate(AppScreens.ErrorScreen.route + "/${AppScreens.HomeScreen.route}")
                homeViewModel.setEnumNull()
            }
        }
    }
}

@Composable
fun emptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = stringResource(id = R.string.no_results),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 18.sp,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homeConstraint(
    list: List<CountryModel>?,
    searchText: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    navController: NavController,
) {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Box (
            Modifier
                .fillMaxWidth()
                .height(120.dp)
                .wrapContentHeight(Alignment.CenterVertically),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.countries),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
        }
        Box (
            Modifier
                .fillMaxWidth()
                .height(100.dp),
        ) {
            SearchBar(
                modifier = Modifier.height(60.dp),
                query = searchText,
                onQueryChange = onSearchQueryChange,
                onSearch = onSearch,
                placeholder = {
                    Text(
                        modifier = Modifier,
                        text = "Search")
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier,
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_mic_24),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                },
                content = {},
                active = false,
                onActiveChange = {},
                tonalElevation = 0.dp
            )
        }
        if (list.isNullOrEmpty()) {
            emptyScreen()
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(1),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    count = list.size,
                    itemContent = { index ->
                        val item = list[index]
                        listRow(
                            item = item,
                            navController = navController
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun listRow(
    item: CountryModel,
    navController: NavController) {
    Row (
        Modifier
            .height(150.dp)
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                val bundle = Bundle()
                bundle.putParcelable("country", item)
                navController.navigate(AppScreens.DetailScreen.route, bundle)
            },
    ) {
        AsyncImage(
            modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.flags?.png)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.baseline_image_not_supported_24),
            contentDescription = item.flags?.png ?: "",
            contentScale = ContentScale.Crop,
            )
        Column (
            modifier = Modifier,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier,
                text = item.name?.common ?: "???",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                modifier = Modifier,
                text = item.name?.official ?: "???",
                color = Color.DarkGray,
            )
            Text(
                modifier = Modifier,
                text = item.capital?.get(0) ?: "???",
                color = Color.DarkGray,
            )
        }
    }
}

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}