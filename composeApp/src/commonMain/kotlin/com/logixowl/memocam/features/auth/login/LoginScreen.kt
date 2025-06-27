package com.logixowl.memocam.features.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = koinViewModel(),
    onClickRegister: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is LoginEvent.Error -> {
                }

                is LoginEvent.LoginSuccess -> {
                    onNavigateToDashboard.invoke()
                }
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                LoginAction.OnClickRegister -> {
                    onClickRegister.invoke()
                }

                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8F5E8),
                        Color(0xFFF0F8FF)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/Title
            Card(
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 32.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF81C784)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                }
            }

            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Sign in to continue",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email Field
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onAction(LoginAction.OnChangedEmail(it)) },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF81C784),
                    focusedLabelColor = Color(0xFF81C784)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
            )

            // Password Field
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { onAction(LoginAction.OnChangedPassword(it)) },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(
                        onClick = { onAction(LoginAction.OnToggledPasswordVisibility) }
                    ) {
                        Icon(
                            if (uiState.isPasswordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (uiState.isPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF81C784),
                    focusedLabelColor = Color(0xFF81C784)
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
            )

            // Login Button
            Button(
                onClick = { onAction(LoginAction.OnClickLogin) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                ),
                enabled = uiState.enabledLogin
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Sign In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Register Button
            TextButton(
                onClick = { onAction(LoginAction.OnClickRegister) }
            ) {
                Text(
                    "Don't have an account? Sign Up",
                    color = Color(0xFF2E7D32)
                )
            }

            // Error Message
            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    MaterialTheme {
        LoginScreen(
            uiState = LoginUiState(),
            onAction = {},
        )
    }
}
