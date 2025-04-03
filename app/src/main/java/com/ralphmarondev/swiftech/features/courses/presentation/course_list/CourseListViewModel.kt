package com.ralphmarondev.swiftech.features.courses.presentation.course_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseListViewModel : ViewModel() {

    private val _courses = MutableStateFlow<List<User>>(emptyList())
    val courses = _courses.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000)
            _isLoading.value = false
        }
    }
}