package com.ralphmarondev.swiftech.core.di

import com.ralphmarondev.swiftech.core.data.local.database.AppDatabase
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.data.repositories.CourseRepositoryImpl
import com.ralphmarondev.swiftech.core.data.repositories.EvaluationFormRepositoryImpl
import com.ralphmarondev.swiftech.core.data.repositories.UserRepositoryImpl
import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository
import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository
import com.ralphmarondev.swiftech.core.domain.usecases.course.CreateCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.DeleteCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetAllCoursesUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseByStudentIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetStudentInCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.InsertStudentToCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.RemoveStudentInCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.UpdateCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.CreateEvaluationFormUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.DeleteEvaluationFormByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.DeleteQuestionByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.GetAllEvaluationFormsUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.GetEvaluationFormByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.GetQuestionsByEvaluationIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.HasAnyoneAnsweredUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.SaveQuestionToEvaluationFormUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.UpdateEvaluationFormUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.UpdateQuestionByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.CreateUserUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.DeleteUserUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetAllUserByRoleUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetAllUserUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserDetailByUsername
import com.ralphmarondev.swiftech.core.domain.usecases.user.IsUserExistsUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.UpdateUserUseCase
import com.ralphmarondev.swiftech.core.util.ThemeState
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    singleOf(::AppPreferences)
    singleOf(::ThemeState)

    single { AppDatabase.createDatabase(androidContext()) }
    single { get<AppDatabase>().userDao }
    single { get<AppDatabase>().courseDao }
    single { get<AppDatabase>().evaluationFormDao }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<CourseRepository> { CourseRepositoryImpl(get()) }
    single<EvaluationFormRepository> { EvaluationFormRepositoryImpl(get()) }

    factoryOf(::CreateUserUseCase)
    factoryOf(::UpdateUserUseCase)
    factoryOf(::DeleteUserUseCase)
    factoryOf(::GetUserDetailByUsername)
    factoryOf(::IsUserExistsUseCase)
    factoryOf(::GetAllUserUseCase)
    factoryOf(::GetAllUserByRoleUseCase)
    factoryOf(::GetUserByIdUseCase)

    factoryOf(::CreateCourseUseCase)
    factoryOf(::UpdateCourseUseCase)
    factoryOf(::GetAllCoursesUseCase)
    factoryOf(::GetCourseDetailByIdUseCase)
    factoryOf(::GetStudentInCourseUseCase)
    factoryOf(::InsertStudentToCourseUseCase)
    factoryOf(::GetCourseByStudentIdUseCase)
    factoryOf(::DeleteCourseUseCase)
    factoryOf(::RemoveStudentInCourseUseCase)

    factoryOf(::CreateEvaluationFormUseCase)
    factoryOf(::UpdateEvaluationFormUseCase)
    factoryOf(::DeleteEvaluationFormByIdUseCase)
    factoryOf(::GetAllEvaluationFormsUseCase)
    factoryOf(::GetEvaluationFormByIdUseCase)
    factoryOf(::SaveQuestionToEvaluationFormUseCase)
    factoryOf(::GetQuestionsByEvaluationIdUseCase)
    factoryOf(::UpdateQuestionByIdUseCase)
    factoryOf(::DeleteQuestionByIdUseCase)
    factoryOf(::HasAnyoneAnsweredUseCase)
}