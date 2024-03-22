package com.plcoding.contactscomposemultiplatform.contacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactValidator
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactListViewModel(private val contactDataSource: ContactDataSource) : ViewModel() {
    private val _state = MutableStateFlow(ContactListState())
    val state = combine(
        _state,
        contactDataSource.getContacts(),
        contactDataSource.getRecentContacts(5)
    ) { state, contacts, recentContacts ->
        state.copy(contacts = contacts, recentlyAddedContacts = recentContacts)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ContactListState())

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(events: ContactListEvents) {
        when (events) {
            ContactListEvents.DeleteContact -> {
                viewModelScope.launch {
                    _state.value.selectedContact?.id?.let { id ->
                        _state.update { it.copy(isSelectedContactOpen = false) }
                        contactDataSource.deleteContact(id)
                        delay(300L)
                        _state.update { it.copy(selectedContact = null) }
                    }
                }
            }

            ContactListEvents.DismissContact -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isSelectedContactOpen = false,
                            isAddContactSheetOpen = false,
                            firstNameError = null,
                            lastNameError = null,
                            phoneNumberError = null
                        )
                    }
                    delay(300L)
                    newContact = null
                    _state.update {
                        it.copy(
                            selectedContact = null
                        )
                    }
                }
            }

            is ContactListEvents.EditContact -> {
                _state.update {
                    it.copy(
                        selectedContact = null,
                        isAddContactSheetOpen = true,
                        isSelectedContactOpen = false
                    )
                }
                newContact = events.contact
            }

            ContactListEvents.OnAddNewContactClicked -> {
                _state.update {
                    it.copy(isAddContactSheetOpen = true)
                }
                newContact = Contact(
                    id = null,
                    firstName = "",
                    lastName = "",
                    email = "",
                    phoneNumber = "",
                    photoBytes = null
                )
            }

            is ContactListEvents.OnEmailChanged -> {
                newContact = newContact?.copy(email = events.value)
            }

            is ContactListEvents.OnFirstNameChanged -> {
                newContact = newContact?.copy(firstName = events.value)
            }

            is ContactListEvents.OnLastNameChanged -> {
                newContact = newContact?.copy(lastName = events.value)
            }

            is ContactListEvents.OnPhoneNumberChanged -> {
                newContact = newContact?.copy(phoneNumber = events.value)
            }

            is ContactListEvents.OnPhotoPicked -> {
                newContact = newContact?.copy(photoBytes = events.bytes)
            }

            ContactListEvents.SaveContact -> {
                newContact?.let { contact ->
                    val result = ContactValidator.validateContact(
                        contact
                    )

                    val errors = listOfNotNull(
                        result.firstNameError,
                        result.lastNameError,
                        result.emailError,
                        result.phoneNumberError
                    )

                    if (errors.isEmpty()) {
                        _state.update {
                            it.copy(
                                isAddContactSheetOpen = false,
                                firstNameError = null,
                                lastNameError = null,
                                phoneNumberError = null,
                                emailError = null
                            )
                        }
                        viewModelScope.launch {
                            contactDataSource.insertContact(contact)
                            delay(300L) //animation
                            newContact = null
                        }
                    } else {
                        _state.update {
                            it.copy(
                                lastNameError = result.lastNameError,
                                firstNameError = result.firstNameError,
                                phoneNumberError = result.phoneNumberError,
                                emailError = result.emailError
                            )
                        }
                    }
                }
            }

            is ContactListEvents.SelectContact -> {
                _state.update {
                    it.copy(
                        selectedContact = events.contact,
                        isSelectedContactOpen = true
                    )
                }
            }

            else -> {}
        }
    }
}