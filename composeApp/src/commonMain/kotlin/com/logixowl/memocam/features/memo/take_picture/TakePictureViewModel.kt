package com.logixowl.memocam.features.memo.take_picture

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.logixowl.memocam.core.BaseViewModel
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

class TakePictureViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TakePictureEvent>() {

    private val argument = savedStateHandle.toRoute<TakePictureNavigation>()

    lateinit var permissionsController: PermissionsController

    private val _state = MutableStateFlow(TakePictureUiState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TakePictureUiState()
        )

    fun initViewModel(
        permissionsController: PermissionsController
    ) {
        this.permissionsController = permissionsController
    }

    fun onAction(action: TakePictureAction) {

    }

}
