package xyz.deliverease.deliverease.android.ui.components.display

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import xyz.deliverease.deliverease.delivery.LocationDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationAutofill(
    modifier: Modifier = Modifier,
    label: String,
    input: String,
    onInputChange: (String) -> Unit,
    onSelectedChange: (LocationDto) -> Unit,
    suggestions: List<LocationDto>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = onInputChange,
            label = { Text(label) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable)
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            suggestions.forEach {
                DropdownMenuItem(
                    text = { Text(it.formattedAddress()) },
                    onClick = {
                        onSelectedChange(it)
                        expanded = false
                    }
                )
            }
        }
    }
}