package xyz.deliverease.deliverease.android.ui.input

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import xyz.deliverease.deliverease.android.ui.screens.Location


@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun LocationInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onChange: (Location) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { TODO("Handle location change with ViewModel and service") },
        label = { Text(label) },
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun TextInputBox(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        minLines = 3,
        modifier = modifier
    )
}

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    changePasswordVisibility: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = changePasswordVisibility) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                )
            }
        }
    )
}