package com.ralphmarondev.swiftech.teacher_features.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.admin_features.reports.presentation.ReportScreen
import com.ralphmarondev.swiftech.teacher_features.home.presentation.HomeScreen
import com.ralphmarondev.swiftech.teacher_features.profile.presentation.ProfileScreen

@Composable
fun TeacherNavigation(
    username: String,
    onLogout: () -> Unit,
    navigateToSettings: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = TeacherRoutes.Home
    ) {
        composable<TeacherRoutes.Home> {
            HomeScreen(
                username = username,
                onCourseClick = { courseId ->
                    navController.navigate(TeacherRoutes.Reports(courseId)) {
                        launchSingleTop = true
                    }
                },
                onLogout = onLogout,
                onAccountCardClick = { username ->
                    navController.navigate(TeacherRoutes.Profile(username)) {
                        launchSingleTop = true
                    }
                },
                navigateToSettings = navigateToSettings
            )
        }
        composable<TeacherRoutes.Reports> {
            val courseId = it.arguments?.getInt("courseId")
            Log.d("App", "AdminNavigation, courseId: `$courseId`")
            ReportScreen(
                courseId = courseId ?: 0,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<TeacherRoutes.Profile> {
            val usernameArgs = it.arguments?.getString("username")
            ProfileScreen(
                usernameArgs = usernameArgs ?: "No username provided.",
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}