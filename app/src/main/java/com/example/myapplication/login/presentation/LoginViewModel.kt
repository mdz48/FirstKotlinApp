package com.example.myapplication.login.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel {
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    val username = _username.asStateFlow()
    val password = _password.asStateFlow()
    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun onUsernameChange(it: String){
//        _username = it;
    }


}