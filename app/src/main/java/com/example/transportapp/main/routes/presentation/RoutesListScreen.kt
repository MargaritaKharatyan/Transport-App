package com.example.transportapp.main.routes.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.transportapp.R
import com.example.transportapp.main.routes.presentation.components.RoutesListUiState
import com.example.transportapp.main.routes.presentation.components.SearchBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RoutesListScreen(
    modifier: Modifier = Modifier,
    viewModel: RoutesViewModel = org.koin.androidx.compose.koinViewModel(),
    navigateToSignIn: () -> Unit,
    navigateToMap: (Int) -> Unit = {},
) {
    val routes = viewModel.routes
    val routesState by viewModel.routesUiState.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val isRefreshing = routesState is RoutesListUiState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.light_milk))
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            val image = painterResource(R.drawable.ic_back)
            IconButton(
                onClick = { navigateToSignIn() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-12).dp)
            ) {
                Icon(
                    painter = image,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        SearchBar(
            text = searchText,
            onClearClick = { viewModel.onClearSearch() },
            onSearchClick = { viewModel.onSearchText(it) })

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable {
                    viewModel.sortRoutesByTime()
                },
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(R.string.filter_text),
                color = colorResource(R.color.green_btn),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(R.drawable.arrow_down_up),
                contentDescription = "Sort",
                tint = colorResource(R.color.green_btn),
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = { viewModel.fetchRoutes() }
        ) {
            when (val uiState = routesState) {
                is RoutesListUiState.Success -> {
                    if (uiState.data.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(R.string.data_loading_result),
                            color = Color.Gray
                        )
                    } else {
                        LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
                            items(
                                items = uiState.data,
                                key = { route -> route.id }
                            ) { route ->
                                RouteCard(
                                    route = route,
                                    navigateToMap = { navigateToMap(route.id) },
                                    modifier = Modifier.animateItem() // Теперь будет летать!
                                )
                            }
                        }

                    }
                }

                RoutesListUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 4.dp, color = colorResource(R.color.dark_blue)
                        )
                    }
                }

                is RoutesListUiState.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.general_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}