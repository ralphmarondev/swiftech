package com.ralphmarondev.swiftech.features.teachers.di

import com.ralphmarondev.swiftech.features.teachers.presentation.new_teacher.NewTeacherViewModel
import com.ralphmarondev.swiftech.features.teachers.presentation.teacher_list.TeacherListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val teacherModule = module {
    viewModelOf(::TeacherListViewModel)
    viewModelOf(::NewTeacherViewModel)
}