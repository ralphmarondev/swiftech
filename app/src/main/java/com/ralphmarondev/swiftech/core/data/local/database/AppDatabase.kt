package com.ralphmarondev.swiftech.core.data.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ralphmarondev.swiftech.core.data.local.database.dao.CourseDao
import com.ralphmarondev.swiftech.core.data.local.database.dao.EvaluationFormDao
import com.ralphmarondev.swiftech.core.data.local.database.dao.UserDao
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.EvaluationAnswer
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.core.domain.model.EvaluationResponse
import com.ralphmarondev.swiftech.core.domain.model.StudentCourseCrossRef
import com.ralphmarondev.swiftech.core.domain.model.User

@Database(
    entities = [
        User::class,
        Course::class,
        StudentCourseCrossRef::class,
        EvaluationForm::class,
        EvaluationQuestion::class,
        EvaluationResponse::class,
        EvaluationAnswer::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val courseDao: CourseDao
    abstract val evaluationFormDao: EvaluationFormDao

    companion object {
        private const val NAME = "swiftech_database"

        fun createDatabase(context: Context): AppDatabase {
            return try {
                Log.d("App", "Creating database...")
                val database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    NAME
                ).build()
                Log.d("App", "Database `$NAME` created successfully!")
                database
            } catch (e: Exception) {
                Log.e("App", "Error creating database: ${e.message}")
                throw e
            }
        }
    }
}