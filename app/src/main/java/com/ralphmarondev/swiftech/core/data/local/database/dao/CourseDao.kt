package com.ralphmarondev.swiftech.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.StudentCourseCrossRef
import com.ralphmarondev.swiftech.core.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCourse(course: Course)

    @Query("UPDATE course SET isDeleted = 1 WHERE id = :id")
    suspend fun deleteCourse(id: Int)

    @Query("SELECT * FROM course WHERE id = :id")
    suspend fun getCourseDetailById(id: Int): Course?

    @Query("SELECT * FROM course")
    fun getAllCourses(): Flow<List<Course>>

    // enroll student in a course
    @Upsert
    suspend fun insertStudentToCourse(crossRef: StudentCourseCrossRef)

    @Query(
        """
        SELECT * FROM user
        INNER JOIN student_course ON user.id = student_course.studentId
        WHERE student_course.courseId = :courseId AND user.isDeleted = 0
    """
    )
    fun getStudentsInCourse(courseId: Int): Flow<List<User>>
}