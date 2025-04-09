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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isError: Boolean = false,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        isError = isError,
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isError: Boolean = false,
    readOnly: Boolean = false,
    trailingIcon: ImageVector,
    trailingIconHandler: () -> Unit,
    onChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        isError = isError,
        singleLine = true,
        readOnly = readOnly,
        trailingIcon = {
            IconButton(onClick = trailingIconHandler) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = "Trailing icon"
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean = false,
    readOnly: Boolean = false,
    trailingIcon: ImageVector,
    trailingIconHandler: () -> Unit,
    onChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        isError = isError,
        singleLine = true,
        readOnly = readOnly,
        trailingIcon = {
            IconButton(onClick = trailingIconHandler) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = "Trailing icon"
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
fun TextInputBox(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isError: Boolean = false,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        isError = isError,
        minLines = 3,
        modifier = modifier
    )
}

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isError: Boolean = false,
    onChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    changePasswordVisibility: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        isError = isError,
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