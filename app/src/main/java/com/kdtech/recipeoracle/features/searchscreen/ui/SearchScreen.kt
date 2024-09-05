package com.kdtech.recipeoracle.features.searchscreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kdtech.recipeoracle.common.ScreenEvent
import com.kdtech.recipeoracle.common.toast
import com.kdtech.recipeoracle.features.searchscreen.presentation.models.SearchState
import com.kdtech.recipeoracle.features.searchscreen.presentation.viewmodel.SearchViewModel
import com.kdtech.recipeoracle.resources.StringResources
import com.kdtech.recipeoracle.resources.components.LottieLoader
import com.kdtech.recipeoracle.resources.compositions.RecipeCard

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel
) {
    val state by viewModel.state.collectAsState(SearchState())
    val lazyGridState = rememberLazyGridState()
    val query by viewModel.queryFlow.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    var isKeyboardOpened by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        keyboardController?.show()
    }
    LaunchedEffect(state.screenEvent) {
        val event = state.screenEvent
        if (event is ScreenEvent.ShowToast) {
            context.toast(message = event.message, resourceId = event.resourceId)
            viewModel.onScreenEventsShown()
        } else if (event is ScreenEvent.ShowSnackBar) {
            snackBarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.actionLabel,
                duration = event.duration
            )
            viewModel.onScreenEventsShown()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            text = query,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            searchModifier = Modifier
                .fillMaxWidth().wrapContentHeight()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    if (isFocused.not() && isKeyboardOpened.not()) {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                        isKeyboardOpened = true
                    }
                },
            onValueChange = viewModel::setQuery,
            placeholderText = stringResource(StringResources.search),
            onCloseClick = viewModel::onCloseCLick,
            keyboardController = keyboardController
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = lazyGridState
        ) {
            itemsIndexed(state.recipesList) { index, item ->
                RecipeCard(
                    recipeTitle = item.recipeName,
                    recipeId = item.recipeId,
                    recipeMakingTime = " ${item.prepTime} Minutes",
                    recipeImageUrl = item.imageUrl,
                    onClick = viewModel::onDetailsClick
                )
            }
        }
        if (state.isLoading) {
            LottieLoader()
        }
    }
}