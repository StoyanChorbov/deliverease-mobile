package xyz.deliverease.deliverease.android.ui.screens.delivery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NavigateNext
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import xyz.deliverease.deliverease.android.LocalNavController
import xyz.deliverease.deliverease.android.navigateTo
import xyz.deliverease.deliverease.android.ui.display.Loading
import xyz.deliverease.deliverease.android.ui.display.LocationAutofill
import xyz.deliverease.deliverease.android.ui.input.CheckboxWithLabel
import xyz.deliverease.deliverease.android.ui.input.DropdownWithLabel
import xyz.deliverease.deliverease.android.ui.input.TextInputBox
import xyz.deliverease.deliverease.android.ui.input.TextInputField
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.Location
import xyz.deliverease.deliverease.delivery.add.AddDeliveryEvent
import xyz.deliverease.deliverease.delivery.add.AddDeliveryState
import xyz.deliverease.deliverease.delivery.add.AddDeliveryViewModel

@Composable
fun AddDeliveryScreenRoot(
    modifier: Modifier = Modifier,
    addDeliveryViewModel: AddDeliveryViewModel = koinViewModel()
) {
    val navController = LocalNavController.current
    val addDeliveryState by addDeliveryViewModel.addDeliveryState.collectAsState()

    AddDeliveryScreen(
        modifier = modifier,
        addDeliveryState = addDeliveryState,
        setName = { addDeliveryViewModel.setName(it) },
        setStartLocation = { addDeliveryViewModel.setStartLocation(it) },
        setEndLocation = { addDeliveryViewModel.setEndLocation(it) },
        setDescription = { addDeliveryViewModel.setDescription(it) },
        setDeliveryCategory = { addDeliveryViewModel.setDeliveryCategory(it) },
        setIsFragile = { addDeliveryViewModel.setIsFragile(it) },
        onAddDelivery = {
            // TODO: Switch with events
//            addDeliveryViewModel.addDelivery()

//            if (addDeliveryViewModel.addDeliveryState.value.isInputValid) {
                navigateTo(
                    navController = navController,
                    NavDestination.AddDeliveryRecipients.route
                )
//            }
        }
    )
}

@Composable
fun AddDeliveryScreen(
    modifier: Modifier = Modifier,
    addDeliveryState: AddDeliveryState,
    setName: (String) -> Unit,
    setStartLocation: (Location) -> Unit,
    setEndLocation: (Location) -> Unit,
    setDescription: (String) -> Unit,
    setDeliveryCategory: (DeliveryCategory) -> Unit,
    setIsFragile: (Boolean) -> Unit,
    onAddDelivery: () -> Unit
) {

    Column(
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
            value = addDeliveryState.name,
            onChange = setName
        )
        // TODO: Add option for selecting on a map with popup
        LocationAutofill(
            label = "Start Location",
            setLocation = setStartLocation
        )
        // TODO: Add option for selecting on a map with popup
        LocationAutofill(
            label = "End Location",
            setLocation = setEndLocation
        )
        TextInputBox(
            label = "Description",
            value = addDeliveryState.description,
            onChange = setDescription
        )
        DropdownWithLabel(
            label = "Category",
            items = DeliveryCategory.entries.map { it.toString() },
            readOnly = true,
            onSelectedChange = { setDeliveryCategory(DeliveryCategory.valueOf(it)) }
        )
        CheckboxWithLabel(
            label = "Is the package fragile?",
            checked = addDeliveryState.isFragile,
            onChange = setIsFragile
        )
        if (addDeliveryState.isLoading) {
            Loading()
        } else if (addDeliveryState.hasError) {
            Text(
                text = addDeliveryState.error ?: "An error occurred"
            ) //TODO: Swap with custom ErrorText composable
        } else {
            OutlinedIconButton(
                onClick = onAddDelivery,
                modifier = Modifier.padding(top = 12.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.NavigateNext,
                    contentDescription = "Add recipients"
                )
            }
        }
    }
}

@Composable
fun AddDeliveryLocationScreen(modifier: Modifier = Modifier) {
//    TODO: Implement this screen
//    BasicMapWithMarker(
//        modifier = modifier.fillMaxSize(),
//        onMarkerClick = { }
//    )
}

@Composable
fun AddDeliveryRecipientsScreenRoot(modifier: Modifier = Modifier, addDeliveryViewModel: AddDeliveryViewModel = koinViewModel()) {
    val navController = LocalNavController.current
    val addDeliveryState by addDeliveryViewModel.addDeliveryState.collectAsState()
    val addDeliveryEvent by addDeliveryViewModel.addDeliveryEvent.collectAsState(initial = AddDeliveryEvent.Idle)

    AddDeliveryRecipientsScreen(
        modifier = modifier,
        label = "Recipient",
        addDeliveryState = addDeliveryState,
        onChangeCurrentRecipient = { addDeliveryViewModel.setCurrentRecipient(it) },
        onAddRecipient = {
            addDeliveryViewModel.addRecipient()
        },
        onRemoveRecipient = {
            addDeliveryViewModel.removeRecipient(it)
        }
    )
}

@Composable
fun AddDeliveryRecipientsScreen(
    modifier: Modifier = Modifier,
    label: String,
    addDeliveryState: AddDeliveryState,
    onChangeCurrentRecipient: (String) -> Unit,
    onAddRecipient: () -> Unit,
    onRemoveRecipient: (String) -> Unit,
) {

    // List of the recipients' usernames
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextInputField(
            label = label,
            value = addDeliveryState.currentRecipient,
            onChange = onChangeCurrentRecipient,
            trailingIcon = Icons.Outlined.Add,
            trailingIconHandler = onAddRecipient
        )

        addDeliveryState.recipients.forEach {
            TextInputField(
                value = it,
                readOnly = true,
                trailingIcon = Icons.Outlined.Remove,
                trailingIconHandler = { onRemoveRecipient(it) }
            )
        }
    }
}