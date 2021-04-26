package com.dj.brownsmog.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.brownsmog.repository.auth.AuthRepository
import com.dj.brownsmog.repository.main.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    fun register(userId: String, password: String, nickName: String) {
        viewModelScope.launch {
            authRepository.register(userId = userId, password = password, nickName = nickName)
                .collect {
                    if (!it) {
                        _errorMessage.value = "이미 가입되어 있거나 잘못된 시도입니다. 다시 시도해주세요."
                    }
                }
        }
    }

    fun login(userId: String, password: String) {
        viewModelScope.launch {
            authRepository.login(userId = userId, password = password).collect {
                if (!it) {
                    _errorMessage.value = "가입되어 있지 않거나 잘못된 시도입니다. 다시 시도해주세요."
                }
            }
        }
    }
}