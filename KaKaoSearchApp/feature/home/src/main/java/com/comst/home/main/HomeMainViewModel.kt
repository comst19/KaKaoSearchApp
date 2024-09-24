package com.comst.home.main

import com.comst.home.main.HomeMainContract.*
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(

): BaseViewModel<HomeMainUIState, HomeMainSideEffect, HomeMainIntent, HomeMainEvent>(HomeMainUIState()) {

    override fun handleIntent(intent: HomeMainIntent) {

    }

}