package com.comst.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.comst.signin.login.LoginRoute
import com.comst.signin.signup.SignUpRoute
import com.comst.ui.SnackbarToken

fun NavController.navigateSignIn() {
    navigate(SignInRoute.defaultRoute)
}

fun NavController.navigateSignUp() {
    navigate(SignInRoute.signUpRoute)
}

fun NavGraphBuilder.signInNavGraph(
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit,
    onBackClick : () -> Unit
) {
    composable(route = SignInRoute.defaultRoute){
        LoginRoute (
            navigateToSignUp = navigateToSignUp,
            navigateToHome = navigateToHome,
        )
    }

    composable(route = SignInRoute.signUpRoute){
        SignUpRoute(
            navigateToHome = navigateToHome,
            onBackClick = onBackClick
        )
    }
}

object SignInRoute{
    const val defaultRoute = "signIn"
    const val signUpRoute = "signUp"
}