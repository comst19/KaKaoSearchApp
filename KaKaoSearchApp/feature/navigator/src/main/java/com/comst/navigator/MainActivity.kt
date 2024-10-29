package com.comst.navigator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.comst.designsystem.theme.BaseTheme
import com.comst.domain.constants.IMAGE_DOWNLOAD
import com.comst.navigator.MainContract.*
import com.comst.ui.SnackbarToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewmodel: MainViewModel by viewModels()

    private val receiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == IMAGE_DOWNLOAD){
                mainViewmodel.setEvent(
                    MainEvent.ShowSnackbar(
                        SnackbarToken(
                            "이미지 다운로드 완료"
                        )
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseTheme {
                MainScreen(mainViewmodel)
            }
        }

        ContextCompat.registerReceiver(
            this,
            receiver,
            IntentFilter(IMAGE_DOWNLOAD),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}