package com.plcoding.contactscomposemultiplatform.contacts.data

import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.core.data.ImageStorage
import database.ContactEntity

suspend fun ContactEntity.mapToModel(imageStorage: ImageStorage): Contact = Contact(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    phoneNumber = phoneNumber,
    photoBytes = imagePath?.let { imageStorage.getImage(it) }
)