package com.ralphmarondev.swiftech.admin_features.evaluation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.evaluation_detail.EvaluationDetailScreen
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.evaluation_list.EvaluationListScreen
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.new_evaluation.NewEvaluationScreen
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.update_evaluation.UpdateEvaluationScreen
import kotlinx.serialization.Serializable

object EvaluationRoutes {
    @Serializable
    data object EvaluationList

    @Serializable
    data object NewEvaluation

    @Serializable
    data class EvaluationDetail(val id: Int)

    @Serializable
    data class UpdateEvaluation(val id: Int)
}

@Composable
fun EvaluationNavigation(
    navigateBack: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = EvaluationRoutes.EvaluationList
    ) {
        composable<EvaluationRoutes.EvaluationList> {
            EvaluationListScreen(
                navigateBack = navigateBack,
                newEvaluation = {
                    navController.navigate(EvaluationRoutes.NewEvaluation) {
                        launchSingleTop = true
                    }
                },
                onEvaluationClick = { id ->
                    navController.navigate(EvaluationRoutes.EvaluationDetail(id)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<EvaluationRoutes.NewEvaluation> {
            NewEvaluationScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<EvaluationRoutes.EvaluationDetail> {
            val id = it.arguments?.getInt("id")
            EvaluationDetailScreen(
                id = id ?: 0,
                navigateBack = {
                    navController.navigateUp()
                },
                onUpdateEvaluationDetailClick = { formId ->
                    navController.navigate(EvaluationRoutes.UpdateEvaluation(formId)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<EvaluationRoutes.UpdateEvaluation> {
            val id = it.arguments?.getInt("id")
            UpdateEvaluationScreen(
                id = id ?: 0,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}