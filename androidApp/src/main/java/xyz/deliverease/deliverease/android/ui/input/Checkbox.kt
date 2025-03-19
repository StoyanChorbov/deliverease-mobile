package xyz.deliverease.deliverease.android.ui.input

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CheckboxWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    checked: Boolean,
    isError: Boolean = false,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            colors = CheckboxDefaults.colors(checkedColor = if (isError) MaterialTheme.colorScheme.error else Color.Unspecified),
            onCheckedChange = onChange
        )
        Text(label)
    }
}