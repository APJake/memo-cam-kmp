package com.logixowl.memocam.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import coil3.compose.setSingletonImageLoaderFactory
import com.logixowl.memocam.features.splash.SplashNavigation
import com.logixowl.memocam.ui.navigations.AppNavHost
import com.logixowl.memocam.ui.themes.AppTheme
import com.logixowl.memocam.ui.utils.getAsyncImageLoader
import io.ktor.client.HttpClient
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

/**
 * Created by AP-Jake
 * on 26/06/2025
 */

@Composable
@Preview
fun App(
    viewModel: AppViewModel = koinViewModel(),
    httpClient: HttpClient = koinInject(),
) {
    AppTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context, httpClient)
        }
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
        ) {
            AppNavHost(
                navController = navController,
                startDestination = SplashNavigation,
                modifier = Modifier.imePadding(),
            )
        }

    }
}
