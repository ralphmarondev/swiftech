package com.ralphmarondev.swiftech.features.students.presentation.new_student

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.CreateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewStudentViewModel(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()


    fun onFullNameChange(value: String) {
        _fullName.value = value
    }

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun register() {
        viewModelScope.launch {
            val fullName = _fullName.value.trim()
            val username = _username.value.trim()
            val password = _password.value.trim()

            if (fullName.isEmpty() && username.isEmpty() && password.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "All fields are required!"
                )
                return@launch
            }
            if (fullName.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Full name cannot be empty!"
                )
                return@launch
            }
            if (username.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Username cannot be empty!"
                )
                return@launch
            }
            if (password.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Password cannot be empty!"
                )
                return@launch
            }

            createUserUseCase(
                user = User(
                    username = username,
                    password = password,
                    fullName = fullName,
                    role = "Student"
                )
            )
            _response.value = Result(
                success = true,
                message = "Registration successful."
            )
            Log.d("App", "Full name: $fullName, username: $username, password: $password")
            _fullName.value = ""
            _username.value = ""
            _password.value = ""
        }
    }
}