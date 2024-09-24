package com.comst.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.comst.home.main.navigateHome
import com.comst.home.navigation.HomeRoute
import com.comst.signin.navigation.SignInRoute
import com.comst.signin.navigation.navigateSignIn
import com.comst.signin.navigation.navigateSignUp

class MainNavigator(
    val navController: NavHostController
) {
    val bottomTabItems: State<List<MainNavigationTab>> = mutableStateOf(
        MainNavigationTab.entries
    )
    val startDestination = SignInRoute.defaultRoute

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
    val currentTab: MainNavigationTab?
        @Composable get() = currentDestination
            ?.route
            ?.let { MainNavigationTab.find(it) }

    fun navigate(tab: MainNavigationTab){
        val navOptions = navOptions {
            popUpTo(HomeRoute.defaultRoute) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when(tab){
            MainNavigationTab.HOME -> navController.navigateHome(navOptions)
        }
    }

    /**
     * Home Navigation
     */
    fun navigateHomeAndClearBackStack() {
        val navOptions = navOptions {
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        }
        navController.navigate(HomeRoute.defaultRoute, navOptions)
    }

    /**
     * SignIn Navigation
     */
    fun navigateSignIn() {
        navController.navigateSignIn()
    }

    fun navigateSignUp() {
        navController.navigateSignUp()
    }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainNavigationTab
    }

    fun popBackStack() {
        navController.popBackStack()
    }

}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}