package com.plcoding.contactscomposemultiplatform.contacts.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.presentation.ContactListEvents
import com.plcoding.contactscomposemultiplatform.contacts.presentation.ContactListState
import com.plcoding.contactscomposemultiplatform.contacts.presentation.components.ContactPhoto
import com.plcoding.contactscomposemultiplatform.contacts.presentation.components.SimpleBottomSheet
import kotlinx.datetime.Clock

@Composable
fun AddContactSheet(
    modifier: Modifier = Modifier,
    state: ContactListState,
    newContact: Contact?,
    onEvent: (ContactListEvents) -> Unit,
    isOpen: Boolean,
) {
    SimpleBottomSheet(visible = isOpen, modifier = modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(60.dp))
                if (newContact?.photoBytes == null) {
                    Box(
                        modifier = Modifier.size(150.dp).clip(RoundedCornerShape(40))
                            .background(MaterialTheme.colorScheme.secondaryContainer).clickable {
                                onEvent(ContactListEvents.OnAddPhotoClicked)
                            }.border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                shape = RoundedCornerShape(40)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add photo",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    ContactPhoto(
                        modifier = Modifier.size(150.dp)
                            .clickable { onEvent(ContactListEvents.OnAddPhotoClicked) },
                        contact = newContact,
                    )
                }

                Spacer(Modifier.height(16.dp))
                ContactTextField(
                    value = newContact?.firstName ?: "",
                    placeholder = "First name",
                    error = state.firstNameError,
                    onValueChanged = {
                        onEvent(ContactListEvents.OnFirstNameChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                ContactTextField(
                    value = newContact?.lastName ?: "",
                    placeholder = "Last name",
                    error = state.lastNameError,
                    onValueChanged = {
                        onEvent(ContactListEvents.OnLastNameChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                ContactTextField(
                    value = newContact?.email ?: "",
                    placeholder = "Email",
                    error = state.emailError,
                    onValueChanged = {
                        onEvent(ContactListEvents.OnEmailChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                ContactTextField(
                    value = newContact?.phoneNumber ?: "",
                    placeholder = "Phone number",
                    error = state.phoneNumberError,
                    onValueChanged = {
                        onEvent(ContactListEvents.OnPhoneNumberChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        onEvent(ContactListEvents.SaveContact)
                    }
                ) {
                    Text(text = "Save")
                }
            }
            IconButton(
                onClick = {
                    onEvent(ContactListEvents.DismissContact)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactTextField(
    value: String,
    placeholder: String,
    error: String?,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        OutlinedTextField(
            value = value,
            placeholder = {
                Text(text = placeholder)
            },
            onValueChange = onValueChanged,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}