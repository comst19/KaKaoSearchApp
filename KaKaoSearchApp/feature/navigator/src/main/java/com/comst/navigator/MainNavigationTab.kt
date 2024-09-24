package com.comst.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.comst.favorite_shared_preferences.navigation.FavoriteSharedPreferencesRoute
import com.comst.home.navigation.HomeRoute
import com.comst.search_custom_paging.navigation.SearchCustomPagingRoute

enum class MainNavigationTab(
    @DrawableRes val iconId: Int,
    val unSelectedIconTint: Color,
    @StringRes val labelId: Int,
    val route: String,
) {
    HOME(
        iconId = R.drawable.main_bottom_nav_icon_home_24,
        unSelectedIconTint = Color.Black,
        labelId = R.string.bottom_navigation_tab_label_home,
        route = HomeRoute.defaultRoute,
    ),

    CUSTOM_SEARCH(
        iconId = R.drawable.main_bottom_nav_icon_search_24,
        unSelectedIconTint = Color.Black,
        labelId = R.string.bottom_navigation_tab_label_custom,
        route = SearchCustomPagingRoute.defaultRoute,
    ),

    SHARED_PREFERENCE_FAVORITE(
        iconId = R.drawable.main_bottom_nav_icon_favorite_24,
        unSelectedIconTint = Color.Black,
        labelId = R.string.bottom_navigation_tab_label_shared_preferences,
        route = FavoriteSharedPreferencesRoute.defaultRoute,
    ),
    ;

    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainNavigationTab? {
            return entries.find { it.route == route }
        }
    }
}