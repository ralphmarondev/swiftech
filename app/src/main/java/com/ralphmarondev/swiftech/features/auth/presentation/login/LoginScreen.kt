package com.ralphmarondev.swiftech.features.auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import com.ralphmarondev.swiftech.features.auth.presentation.login.components.ForgotPasswordDialog
import com.ralphmarondev.swiftech.features.auth.presentation.login.components.NormalTextField
import com.ralphmarondev.swiftech.features.auth.presentation.login.components.PasswordTextField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccessful: () -> Unit
) {
    val themeState = LocalThemeState.current
    val viewModel: LoginViewModel = koinViewModel()
    val username = viewModel.username.collectAsState().value
    val password = viewModel.password.collectAsState().value
    val rememberMe = viewModel.rememberMe.collectAsState().value
    val showPasswordDialog = viewModel.showForgotPasswordDialog.collectAsState().value
    val response = viewModel.response.collectAsState().value

    val focusManager = LocalFocusManager.current

    LaunchedEffect(response) {
        response?.let {
            if (it.success) {
                onLoginSuccessful()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login"
                    )
                },
                actions = {
                    IconButton(onClick = themeState::toggleTheme) {
                        val imageVector =
                            if (themeState.darkTheme.value) Icons.Outlined.LightMode else Icons.Outlined.DarkMode
                        Icon(
                            imageVector = imageVector,
                            contentDescription = "Theme switcher"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Welcome to Swiftech!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Text(
                        text = "Please login to continue.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    NormalTextField(
                        value = username,
                        onValueChange = viewModel::onUsernameChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        label = "Username",
                        leadingIcon = Icons.Outlined.AccountBox,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Next)
                            }
                        )
                    )

                    PasswordTextField(
                        value = password,
                        onValueChange = viewModel::onPasswordChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        label = "Password",
                        leadingIcon = Icons.Outlined.Password,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp, bottom = 4.dp, top = 4.dp)
                            .clickable { viewModel.toggleRememberMe() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { viewModel.toggleRememberMe() },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                uncheckedColor = MaterialTheme.colorScheme.secondary
                            )
                        )
                        Text(
                            text = "Remember me",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            color = when (rememberMe) {
                                true -> MaterialTheme.colorScheme.primary
                                false -> MaterialTheme.colorScheme.secondary
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.login()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "LOGIN",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.W500,
                            fontSize = 16.sp
                        )
                    }

                    TextButton(
                        onClick = viewModel::toggleForgotPasswordDialog,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Forgot Password?",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }

    if (showPasswordDialog) {
        ForgotPasswordDialog(
            onDismiss = viewModel::toggleForgotPasswordDialog
        )
    }
}