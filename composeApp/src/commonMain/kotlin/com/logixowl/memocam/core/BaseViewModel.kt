package com.logixowl.memocam.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

abstract class BaseViewModel<E> : ViewModel() {

    private val _event = Channel<E>()
    val event = _event.receiveAsFlow()

    protected open fun shouldEmit(event: E): Boolean {
        return true
    }

    protected fun emitEvent(event: E) {
        if (shouldEmit(event)) {
            viewModelScope.launch {
                _event.trySend(event)
            }
        }
    }

}
