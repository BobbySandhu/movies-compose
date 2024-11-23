package com.atlyssahil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.atlyssahil.common.presentation.App
import com.atlyssahil.ui.theme.AtlysSahilTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AtlysSahilTheme(darkTheme = false) {
                App()
            }
        }
    }
}
