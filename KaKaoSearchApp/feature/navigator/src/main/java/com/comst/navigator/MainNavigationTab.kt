package com.comst.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.comst.home.navigation.HomeRoute

enum class MainNavigationTab(
    @DrawableRes val iconId: Int,
    val iconTint: Color,
    @StringRes val labelId: Int,
    val route: String,
) {
    HOME(
        iconId = R.drawable.home,
        iconTint = Color.Black,
        labelId = R.string.bottom_navigation_tab_label_home,
        route = HomeRoute.defaultRoute,
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