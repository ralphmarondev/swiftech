package com.ralphmarondev.swiftech.student_features.evaluate.domain.repository

import com.ralphmarondev.swiftech.core.domain.model.EvaluationAnswer
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.core.domain.model.EvaluationResponse
import kotlinx.coroutines.flow.Flow

interface EvaluateRepository {
    suspend fun getEvaluationFormDetailById(formId: Int): EvaluationForm
    fun getEvaluationFormQuestionsById(formId: Int): Flow<List<EvaluationQuestion>>

    suspend fun submitEvaluation(response: EvaluationResponse, answers: List<EvaluationAnswer>)
    suspend fun hasStudentAlreadyEvaluated(
        studentId: Int,
        courseId: Int,
        evaluationFormId: Int
    ): Boolean
}