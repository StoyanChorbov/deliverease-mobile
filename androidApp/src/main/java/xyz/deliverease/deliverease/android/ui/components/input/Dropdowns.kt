package xyz.deliverease.deliverease.android.ui.components.input

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.search.autocomplete.PlaceAutocompleteAddress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    items: List<String>,
    readOnly: Boolean = false,
    onSelectedChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by rememberSaveable { mutableStateOf(items.last()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = { selected = it },
            readOnly = readOnly,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        selected = it
                        onSelectedChange(it)
                        expanded = false
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressDropdown(
    modifier: Modifier = Modifier,
    label: String,
    input: String,
    items: Map<PlaceAutocompleteAddress, Point>,
    readOnly: Boolean = false,
    onInputChange: (String) -> Unit,
    onSelectedChange: (Map.Entry<PlaceAutocompleteAddress, Point>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by rememberSaveable { mutableStateOf<PlaceAutocompleteAddress?>(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = {
                onInputChange(it)
                selected = null
            },
            readOnly = readOnly,
            label = { Text(label) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text("${it.key.place}, ${it.key.region}") },
                    onClick = {
                        selected = it.key
                        onSelectedChange(it)
                        expanded = false
                    }
                )
            }
        }
    }
}