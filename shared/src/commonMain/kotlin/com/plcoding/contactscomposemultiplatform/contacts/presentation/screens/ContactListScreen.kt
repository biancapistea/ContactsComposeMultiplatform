package com.plcoding.contactscomposemultiplatform.contacts.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.presentation.ContactListEvents
import com.plcoding.contactscomposemultiplatform.contacts.presentation.ContactListState
import com.plcoding.contactscomposemultiplatform.contacts.presentation.components.ContactListItem
import com.plcoding.contactscomposemultiplatform.contacts.presentation.components.RecentlyAddedContacts
import com.plcoding.contactscomposemultiplatform.core.presentation.ImagePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    state: ContactListState,
    newContact: Contact?,
    onEvent: (ContactListEvents) -> Unit,
    imagePicker: ImagePicker
) {
    imagePicker.registerPicker { imageBytes ->
        onEvent(ContactListEvents.OnPhotoPicked(imageBytes))
    }
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            onEvent(ContactListEvents.OnAddNewContactClicked)
        }, shape = RoundedCornerShape(20.dp), content = {
            Icon(imageVector = Icons.Rounded.PersonAdd, contentDescription = "Add contact")
        })
    }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                RecentlyAddedContacts(
                    contacts = state.recentlyAddedContacts,
                    onClick = { onEvent(ContactListEvents.SelectContact(it)) })
            }
            item {
                Text(
                    text = "My contacts (${state.contacts.size})",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            items(state.contacts) { contact ->
                ContactListItem(
                    contact = contact,
                    modifier = Modifier.fillMaxWidth().padding()
                        .clickable { onEvent(ContactListEvents.SelectContact(contact)) })
            }
        }
    }
    ContactDetailScreen(
        isOpen = state.isSelectedContactOpen,
        onEvent = onEvent,
        selectedContact = state.selectedContact,
    )
    AddContactSheet(
        state = state,
        newContact = newContact,
        onEvent = { event ->
            if (event is ContactListEvents.OnAddPhotoClicked) {
                imagePicker.pickImage()
            }
            onEvent(event)
        },
        isOpen = state.isAddContactSheetOpen
    )
}