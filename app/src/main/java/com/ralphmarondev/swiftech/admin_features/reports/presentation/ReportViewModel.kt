package com.ralphmarondev.swiftech.admin_features.reports.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.admin_features.reports.domain.model.QuestionRatingReport
import com.ralphmarondev.swiftech.admin_features.reports.domain.model.RatingCounts
import com.ralphmarondev.swiftech.admin_features.reports.domain.model.StudentReport
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.ComputeAverageRatingUseCase
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.ComputeRatingCountsUseCase
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.GetQuestionRatingReportsByCourseUseCase
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.GetStudentRatingReportsByCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReportViewModel(
    private val courseId: Int,
    private val computeAverageRatingUseCase: ComputeAverageRatingUseCase,
    private val computeRatingCountsUseCase: ComputeRatingCountsUseCase,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getUserDetailByIdUseCase: GetUserByIdUseCase,
    private val getQuestionRatingReportsByCourseUseCase: GetQuestionRatingReportsByCourseUseCase,
    private val getStudentRatingReportsByCourseUseCase: GetStudentRatingReportsByCourseUseCase
) : ViewModel() {

    private val _teacherName = MutableStateFlow("")
    val teacherName = _teacherName.asStateFlow()

    private val _courseName = MutableStateFlow("")
    val courseName = _courseName.asStateFlow()

    private val _averageRating = MutableStateFlow(0.0)
    val averageRating = _averageRating.asStateFlow()

    private val _ratingCounts = MutableStateFlow(RatingCounts())
    val ratingCounts = _ratingCounts.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    // more information
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab = _selectedTab.asStateFlow()

    private val _questionReports = MutableStateFlow<List<QuestionRatingReport>>(emptyList())
    val questionReports = _questionReports.asStateFlow()

    private val _studentReports = MutableStateFlow<List<StudentReport>>(emptyList())
    val studentReports = _studentReports.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("App", "Loading report data for courseId: $courseId")

            val courseDetail = getCourseDetailByIdUseCase(courseId)
            Log.d("App", "Course detail: $courseDetail")
            _courseName.value = courseDetail?.name ?: "No course name provided"
            _teacherName.value = getUserDetailByIdUseCase(courseDetail?.teacherId ?: -1)?.fullName
                ?: "No teacher name provided"

            val averageJob = launch {
                val average = computeAverageRatingUseCase(courseId).first()
                _averageRating.value = average
                Log.d("App", "Average rating: $average")
            }

            val countsJob = launch {
                val counts = computeRatingCountsUseCase(courseId).first()
                _ratingCounts.value = counts
                Log.d("App", "Rating counts: $counts")
            }

            val reportsJob = launch {
                val reports = getQuestionRatingReportsByCourseUseCase(courseId).first()
                _questionReports.value = reports
                Log.d("App", "Question reports: $reports")
            }

            val studentReportsJob = launch {
                val reports = getStudentRatingReportsByCourseUseCase(courseId).first()
                _studentReports.value = reports
                Log.d("App", "Student reports: $reports")
            }

            // Wait for all jobs to emit once
            averageJob.join()
            countsJob.join()
            reportsJob.join()
            studentReportsJob.join()

            _isLoading.value = false
        }
    }

    fun onSelectedTabValueChange(value: Int) {
        _selectedTab.value = value
    }

    fun computeAverageRating(counts: RatingCounts): Double {
        val totalResponses = counts.excellent + counts.veryGood + counts.good + counts.fair + counts.poor
        if (totalResponses == 0) return 0.0

        val totalScore =
            (counts.excellent * 5) +
                    (counts.veryGood * 4) +
                    (counts.good * 3) +
                    (counts.fair * 2) +
                    (counts.poor * 1)

        return totalScore.toDouble() / totalResponses
    }
}
