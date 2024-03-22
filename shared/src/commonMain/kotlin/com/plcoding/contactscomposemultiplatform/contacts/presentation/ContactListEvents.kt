package com.plcoding.contactscomposemultiplatform.contacts.presentation

import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact

sealed interface ContactListEvents {
    object OnAddNewContactClicked : ContactListEvents
    object DismissContact : ContactListEvents
    data class OnFirstNameChanged(val value: String) : ContactListEvents
    data class OnLastNameChanged(val value: String) : ContactListEvents
    data class OnPhoneNumberChanged(val value: String) : ContactListEvents
    data class OnEmailChanged(val value: String) : ContactListEvents
    class OnPhotoPicked(val bytes: ByteArray) : ContactListEvents
    object OnAddPhotoClicked : ContactListEvents
    object SaveContact : ContactListEvents
    data class SelectContact(val contact: Contact) : ContactListEvents
    data class EditContact(val contact: Contact) : ContactListEvents
    object DeleteContact : ContactListEvents
}