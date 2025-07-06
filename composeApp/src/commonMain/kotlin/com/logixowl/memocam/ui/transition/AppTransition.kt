package com.logixowl.memocam.ui.transition

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry

/**
 * Created by AP-Jake
 * on 07/06/2025
 */

interface AppTransition {
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
    val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
    val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
}
