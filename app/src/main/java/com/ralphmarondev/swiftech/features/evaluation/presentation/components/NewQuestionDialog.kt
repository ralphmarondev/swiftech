package com.ralphmarondev.swiftech.features.evaluation.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.swiftech.core.presentation.NormalTextField
import com.ralphmarondev.swiftech.features.evaluation.presentation.new_evaluation.NewEvaluationViewModel

@Composable
fun NewQuestionDialog(
    viewModel: NewEvaluationViewModel,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val newQuestion = viewModel.newQuestion.collectAsState().value

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "New Question"
            )
        },
        text = {
            NormalTextField(
                value = newQuestion,
                onValueChange = viewModel::onNewQuestionValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                leadingIcon = Icons.Outlined.QuestionAnswer,
                label = "Question",
                placeholder = "Cuteness level"
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = "Confirm"
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Dismiss"
                )
            }
        },
        modifier = modifier
    )
}