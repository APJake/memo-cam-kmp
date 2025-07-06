package com.logixowl.memocam.features.memo.take_picture

import androidx.lifecycle.viewModelScope
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
    val permissionsController: PermissionsController,
) : BaseViewModel<TakePictureEvent>() {

    private val _state = MutableStateFlow(TakePictureUiState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TakePictureUiState()
        )

    fun onAction(action: TakePictureAction) {

    }

}
