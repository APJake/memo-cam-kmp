package com.logixowl.memocam.ui.transition

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Stable
import androidx.navigation.NavBackStackEntry

/**
 * Created by AP-Jake
 * on Feb 11, 2023
 */

@Stable
object DefaultTransition : AppTransition {

    private const val DEFAULT_DURATION = 400

    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            slideInHorizontally(
                initialOffsetX = { 1500 },
                animationSpec = tween(DEFAULT_DURATION)
            )
        }

    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            slideOutHorizontally(
                targetOffsetX = { -1500 },
                animationSpec = tween(DEFAULT_DURATION)
            )
        }

    override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            slideInHorizontally(
                initialOffsetX = { -1500 },
                animationSpec = tween(DEFAULT_DURATION)
            )
        }

    override val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            slideOutHorizontally(
                targetOffsetX = { 1500 },
                animationSpec = tween(DEFAULT_DURATION)
            )
        }

}