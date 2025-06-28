package com.logixowl.memocam.features.memo.dashboard

import androidx.lifecycle.viewModelScope
import com.logixowl.memocam.core.AppLogger
import com.logixowl.memocam.core.BaseViewModel
import com.logixowl.memocam.core.onError
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.domain.repository.MemoRepository
import com.logixowl.memocam.domain.repository.PrefsRepository
import com.logixowl.memocam.mapper.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by AP-Jake
 * on 26/06/2025
 */

class DashboardViewModel(
    private val memoRepository: MemoRepository,
    private val prefsRepository: PrefsRepository,
) : BaseViewModel<DashboardEvent>() {

    private val _state = MutableStateFlow(DashboardUiState())
    val state = _state
        .onStart {
            AppLogger.d("Testing", "DashboardVM onStart")
            observeDashboardData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun loadFolders() {
        viewModelScope.launch {
            memoRepository.getAllFolders()
                .onSuccess { result ->
                    _state.update { st ->
                        st.copy(
                            folders = result.map { it.toUiModel() }.reversed()
                        )
                    }
                }
                .onError {
                    AppLogger.e("DashboardViewModel", "failed to fetch folders: $it")
                }
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun observeDashboardData() {
        _state.update {
            it.copy(isLoading = true)
        }
        observeUserData()
        loadFolders()
    }

    private fun observeUserData() {
        viewModelScope.launch {
            prefsRepository.user.collectLatest { user ->
                _state.update {
                    it.copy(
                        userName = user.username,
                    )
                }
            }
        }
    }
}
