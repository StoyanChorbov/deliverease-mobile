package xyz.deliverease.deliverease.android.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.deliverease.deliverease.android.ui.input.CheckboxWithLabel
import xyz.deliverease.deliverease.android.ui.input.DropdownWithLabel
import xyz.deliverease.deliverease.android.ui.input.LocationInputField
import xyz.deliverease.deliverease.android.ui.input.TextInputBox
import xyz.deliverease.deliverease.android.ui.input.TextInputField

//TODO: Move to shared module
enum class Category {
    FOOD,
    CLOTHES,
    ELECTRONICS,
    OTHER
}

data class Location(val name: String = "", val latitude: Double = 0.0, val longitude: Double = 0.0)



@Composable
fun AddDeliveryScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var startLocation by remember { mutableStateOf(Location()) }
    var endLocation by remember { mutableStateOf(Location()) }
    var primaryRecipient by remember { mutableStateOf("") } // Get only the username
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.OTHER) }
    var isFragile by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Rows: Name, Category, whether it's fragile, Start Location, End Location, Description, Recipient(s)
        // Add picture
        // Buttons: Add Recipient, Submit
        TextInputField(
            label = "Package name",
            value = name,
            onChange = { name = it }
        )
        LocationInputField(
            label = "Start location",
            value = startLocation.name,
            onChange = { startLocation = it }
        )
        LocationInputField(
            label = "End location",
            value = endLocation.name,
            onChange = { endLocation = it }
        )
        TextInputField(
            label = "Primary Recipient",
            value = "",
            onChange = { primaryRecipient = it }
        )
        TextInputBox(
            label = "Description",
            value = description,
            onChange = { description = it }
        )
        DropdownWithLabel(label = "Category", items = Category.entries.map { it.toString() }) {
            category = Category.valueOf(it)
        }
        CheckboxWithLabel(
            label = "Is the package fragile?",
            checked = isFragile,
            onChange = { isFragile = it }
        )
        OutlinedIconButton(
            onClick = { TODO("Switch to next page") },
            modifier = Modifier.padding(top = 12.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.NavigateNext,
                contentDescription = "Add recipients"
            )
        }
    }
}

@Composable
fun AddDeliveryRecipients(modifier: Modifier) {
    val recipients = remember { mutableStateListOf<String>() }

    
}