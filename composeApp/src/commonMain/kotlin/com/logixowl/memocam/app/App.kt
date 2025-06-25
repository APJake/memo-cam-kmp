package com.logixowl.memocam.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.logixowl.memocam.features.auth.login.LoginNavigation
import com.logixowl.memocam.ui.navigations.AppNavHost
import com.logixowl.memocam.ui.themes.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by AP-Jake
 * on 26/06/2025
 */

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

        val startDestination = remember {
            LoginNavigation
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
        ) {
            AppNavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(systemBarsPadding).imePadding(),
            )
        }

    }
}
