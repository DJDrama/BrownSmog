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

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage

    fun setNoError() {
        _errorMessage.value = null
    }

    fun register(userId: String, password: String, nickName: String) {
        viewModelScope.launch {
            if(userId.isEmpty() || userId.isBlank()) {
                _errorMessage.value = "아이디를 입력해주세요."
                return@launch
            }
            if(password.isEmpty() || password.isBlank()){
                _errorMessage.value = "비밀번호를 입력해주세요."
                return@launch
            }
            if(nickName.isEmpty() || nickName.isBlank()){
                _errorMessage.value = "닉네임을 입력해주세요."
                return@launch
            }
            authRepository.checkDuplicate(userId, nickName).collect {
                if (it) {
                    _errorMessage.value = "이미 가입되어 있는 아이디 혹은 닉네임입니다."
                } else {
                    authRepository.register(userId = userId,
                        password = password,
                        nickName = nickName)
                        .collect { isRegistered ->
                            if (!isRegistered) {
                                _errorMessage.value = "잘못된 시도입니다. 다시 시도해주세요."
                            }
                        }
                }
            }

        }
    }

    fun login(userId: String, password: String) {
        viewModelScope.launch {
            authRepository.login(userId = userId, password = password).collect {
                if (!it) {
                    _errorMessage.value = "아이디 및 비밀번호를 확인해주세요."
                }
            }
        }
    }
}