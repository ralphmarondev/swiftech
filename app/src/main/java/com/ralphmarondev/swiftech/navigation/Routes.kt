package com.ralphmarondev.swiftech.navigation

import kotlinx.serialization.Serializable

object Routes {

    @Serializable
    data object Login

    @Serializable
    data class Home(val username: String)

    @Serializable
    data object StudentList

    @Serializable
    data object NewStudent

    @Serializable
    data class StudentDetail(val username: String)
}