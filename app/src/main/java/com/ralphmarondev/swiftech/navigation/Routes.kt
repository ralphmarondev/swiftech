package com.ralphmarondev.swiftech.navigation

import kotlinx.serialization.Serializable

object Routes {

    @Serializable
    data object Login

    @Serializable
    data object Home

    @Serializable
    data object StudentList

    @Serializable
    data object NewStudent

    @Serializable
    data class StudentDetail(val username: String)
}