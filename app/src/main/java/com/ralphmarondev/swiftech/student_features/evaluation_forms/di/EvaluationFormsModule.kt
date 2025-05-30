package com.ralphmarondev.swiftech.student_features.evaluation_forms.di

import com.ralphmarondev.swiftech.student_features.evaluation_forms.data.repository.EvaluationFormsRepositoryImpl
import com.ralphmarondev.swiftech.student_features.evaluation_forms.domain.repository.EvaluationFormsRepository
import com.ralphmarondev.swiftech.student_features.evaluation_forms.domain.usecase.GetEvaluationFormByTermUseCase
import com.ralphmarondev.swiftech.student_features.evaluation_forms.presentation.EvaluationFormViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val evaluationFormsModule = module {
    single<EvaluationFormsRepository> { EvaluationFormsRepositoryImpl(get()) }

    factoryOf(::GetEvaluationFormByTermUseCase)

    viewModelOf(::EvaluationFormViewModel)
}