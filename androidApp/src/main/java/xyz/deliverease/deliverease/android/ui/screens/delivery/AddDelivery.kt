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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import xyz.deliverease.deliverease.android.LocalNavController
import xyz.deliverease.deliverease.android.navigateTo
import xyz.deliverease.deliverease.android.ui.components.display.LoadingIndicator
import xyz.deliverease.deliverease.android.ui.components.display.LocationAutofill
import xyz.deliverease.deliverease.android.ui.components.input.CheckboxWithLabel
import xyz.deliverease.deliverease.android.ui.components.input.DropdownWithLabel
import xyz.deliverease.deliverease.android.ui.components.input.TextInputBox
import xyz.deliverease.deliverease.android.ui.components.input.TextInputField
import xyz.deliverease.deliverease.android.ui.navigation.NavDestination
import xyz.deliverease.deliverease.delivery.DeliveryCategory
import xyz.deliverease.deliverease.delivery.LocationDto
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
    val addDeliveryEvent by addDeliveryViewModel.addDeliveryEvent.collectAsState(initial = AddDeliveryEvent.Idle)
    var screenIsAddRecipients by remember { mutableStateOf(false) }

    when (addDeliveryEvent) {
        is AddDeliveryEvent.Navigate -> {
            navigateTo(
                navController = navController,
                "${NavDestination.DeliveryDetails.route}/${(addDeliveryEvent as AddDeliveryEvent.Navigate).deliveryId}"
            )
        }

        else -> {}
    }

    if (!screenIsAddRecipients) {
        AddDeliveryScreen(
            modifier = modifier,
            addDeliveryState = addDeliveryState,
            setName = { addDeliveryViewModel.onEvent(AddDeliveryEvent.Input.SetName(it)) },
            setDescription = { addDeliveryViewModel.onEvent(AddDeliveryEvent.Input.SetDescription(it)) },
            setStartLocationQuery = {
                addDeliveryViewModel.onEvent(
                    AddDeliveryEvent.Input.SetStartLocationQuery(
                        it
                    )
                )
            },
            setStartLocation = {
                addDeliveryViewModel.onEvent(
                    AddDeliveryEvent.Input.SetStartLocation(it)
                )
            },
            setEndLocationQuery = {
                addDeliveryViewModel.onEvent(
                    AddDeliveryEvent.Input.SetEndLocationQuery(
                        it
                    )
                )
            },
            setEndLocation = {
                addDeliveryViewModel.onEvent(
                    AddDeliveryEvent.Input.SetEndLocation(it)
                )
            },
            setDeliveryCategory = {
                addDeliveryViewModel.onEvent(
                    AddDeliveryEvent.Input.SetDeliveryCategory(it)
                )
            },
            setIsFragile = { addDeliveryViewModel.onEvent(AddDeliveryEvent.Input.SetIsFragile(it)) },
            onAddRecipients = { screenIsAddRecipients = true }
        )
    } else {
        AddDeliveryRecipientsScreen(
            modifier = modifier,
            label = "Recipient",
            addDeliveryState = addDeliveryState,
            onChangeCurrentRecipient = {
                addDeliveryViewModel.onEvent(
                    AddDeliveryEvent.Input.ChangeCurrentRecipient(
                        it
                    )
                )
            },
            onAddRecipient = {
                addDeliveryViewModel.onEvent(AddDeliveryEvent.Input.AddRecipient)
            },
            onRemoveRecipient = {
                addDeliveryViewModel.onEvent(AddDeliveryEvent.Input.RemoveRecipient(it))
            },
            onSubmit = {
                addDeliveryViewModel.onEvent(AddDeliveryEvent.Submit)
            }
        )
    }
}

@Composable
fun AddDeliveryScreen(
    modifier: Modifier = Modifier,
    addDeliveryState: AddDeliveryState,
    setName: (String) -> Unit,
    setStartLocationQuery: (String) -> Unit,
    setStartLocation: (LocationDto) -> Unit,
    setEndLocationQuery: (String) -> Unit,
    setEndLocation: (LocationDto) -> Unit,
    setDescription: (String) -> Unit,
    setDeliveryCategory: (DeliveryCategory) -> Unit,
    setIsFragile: (Boolean) -> Unit,
    onAddRecipients: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextInputField(
            label = "Package name",
            value = addDeliveryState.name,
            onChange = setName
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
        LocationAutofill(
            label = "Start Location",
            input = addDeliveryState.startLocationQuery,
            onInputChange = setStartLocationQuery,
            onSelectedChange = setStartLocation,
            suggestions = addDeliveryState.startLocationSuggestions
        )
        LocationAutofill(
            label = "End Location",
            input = addDeliveryState.endLocationQuery,
            onInputChange = setEndLocationQuery,
            onSelectedChange = setEndLocation,
            suggestions = addDeliveryState.endLocationSuggestions
        )
        CheckboxWithLabel(
            label = "Is the package fragile?",
            checked = addDeliveryState.isFragile,
            onChange = setIsFragile
        )
        if (addDeliveryState.isLoading) {
            LoadingIndicator()
        } else if (addDeliveryState.hasError) {
            Text(
                text = addDeliveryState.error ?: "An error occurred",
                color = MaterialTheme.colorScheme.error
            )
        } else {
            OutlinedIconButton(
                onClick = onAddRecipients,
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
fun AddDeliveryRecipientsScreenRoot(
    modifier: Modifier = Modifier,
    addDeliveryViewModel: AddDeliveryViewModel = koinViewModel()
) {
    val navController = LocalNavController.current
    val addDeliveryState by addDeliveryViewModel.addDeliveryState.collectAsState()
    val addDeliveryEvent by addDeliveryViewModel.addDeliveryEvent.collectAsState(initial = AddDeliveryEvent.Idle)

    when (addDeliveryEvent) {
        is AddDeliveryEvent.Navigate -> {
//            navigateTo(
//                navController = navController,
//                NavDestination.DeliveryDetails.route
//            )
        }

        else -> {}
    }


}

@Composable
fun AddDeliveryRecipientsScreen(
    modifier: Modifier = Modifier,
    label: String,
    addDeliveryState: AddDeliveryState,
    onChangeCurrentRecipient: (String) -> Unit,
    onAddRecipient: () -> Unit,
    onRemoveRecipient: (String) -> Unit,
    onSubmit: () -> Unit,
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

        if (addDeliveryState.isLoading) {
            LoadingIndicator()
        } else if (addDeliveryState.hasError) {
            Text(
                text = addDeliveryState.error ?: "An error occurred"
            )
        } else {
            OutlinedButton(
                onClick = { onSubmit() },
                modifier = Modifier.padding(top = 12.dp),
            ) {
                Text("Add delivery")
            }
        }
    }
}