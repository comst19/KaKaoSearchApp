package com.comst.navigator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.comst.designsystem.component.snackbar.KaKaoSnackbar
import com.comst.designsystem.theme.Background100
import com.comst.favorite_shared_preferences.navigation.favoriteSharedPreferencesNavGraph
import com.comst.home.navigation.homeNavGraph
import com.comst.navigator.MainContract.*
import com.comst.search_custom_paging.navigation.searchCustomPagingRouteNavGraph
import com.comst.signin.navigation.signInNavGraph
import com.comst.ui.extension.collectAsStateWithLifecycle
import com.comst.ui.extension.collectWithLifecycle

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navigator: MainNavigator = rememberMainNavigator(),
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.effect.collectWithLifecycle { effect ->

    }

    Scaffold(
        bottomBar = {
            BottomNav(
                visible = navigator.shouldShowBottomBar(),
                modifier = Modifier,
                itemClick = navigator::navigate,
                selectedTab = navigator.currentTab ?: MainNavigationTab.HOME,
                bottomTavItems = navigator.bottomTabItems.value
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            enterTransition = { fadeIn(animationSpec = tween(0)) },
            exitTransition = { fadeOut(animationSpec = tween(0)) },
        ) {
            signInNavGraph(
                navigateToSignUp = navigator::navigateSignUp,
                navigateToHome = navigator::navigateHomeAndClearBackStack,
                onShowSnackBar = { snackbarToken ->
                    viewModel.setEvent(MainEvent.ShowSnackbar(snackbarToken))
                },
                onBackClick = navigator::popBackStack
            )

            homeNavGraph(
                padding = innerPadding,
                onShowSnackbar = { snackbarToken ->
                    viewModel.setEvent(MainEvent.ShowSnackbar(snackbarToken))
                },
                navigateBack = navigator::popBackStack
            )

            favoriteSharedPreferencesNavGraph(
                padding = innerPadding,
                navigateFavoriteSharedPreferences = navigator::navigateFavoriteSharedPreferences,
                onShowSnackbar = { snackbarToken ->
                    viewModel.setEvent(MainEvent.ShowSnackbar(snackbarToken))
                },
                navigateBack = navigator::popBackStack
            )

            searchCustomPagingRouteNavGraph(
                padding = innerPadding,
                navigateSearchCustomPaging = navigator::navigateSearchCustomPaging,
                onShowSnackbar = { snackbarToken ->
                    viewModel.setEvent(MainEvent.ShowSnackbar(snackbarToken))
                },
                navigateBack = navigator::popBackStack
            )
        }
        with(uiState){
            KaKaoSnackbar(
                modifier = Modifier.padding(innerPadding),
                visible = snackbarVisible,
                message = snackbarToken.message,
                actionIconId = snackbarToken.actionIcon,
                actionButtonText = snackbarToken.actionButtonText,
                onClickActionButton = snackbarToken.onClickActionButton,
            )
        }
    }
}

@Composable
fun BottomNav(
    visible: Boolean,
    modifier: Modifier,
    itemClick: (MainNavigationTab) -> Unit = {},
    selectedTab: MainNavigationTab,
    bottomTavItems: List<MainNavigationTab>? = null,
) {
    val selectedItem = rememberUpdatedState(newValue = selectedTab)

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) },
    ) {
        Surface(
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            NavigationBar(
                modifier = modifier,
                containerColor = Background100,
            ) {
                bottomTavItems?.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = item.iconId),
                                contentDescription = null,
                                tint = if (selectedItem.value == item) {
                                    Color.Unspecified
                                } else {
                                    item.unSelectedIconTint
                                }
                            )
                        },
                        label = {
                            Text(text = stringResource(id = item.labelId))
                        },
                        selected = selectedItem.value == item,
                        onClick = {
                            itemClick(item)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White
                        )
                    )
                }
            }
        }
    }
}