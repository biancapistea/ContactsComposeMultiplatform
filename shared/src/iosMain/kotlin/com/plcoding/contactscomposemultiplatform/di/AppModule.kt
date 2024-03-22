package com.plcoding.contactscomposemultiplatform.di

import com.plcoding.contactscomposemultiplatform.contacts.data.SqlDelightContactDataSource
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.core.data.DatabaseDriverFactory
import com.plcoding.contactscomposemultiplatform.core.data.ImageStorage
import com.plcoding.contactsmultiplatform.database.ContactDatabase


actual class AppModule() {
    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            db = ContactDatabase(
                DatabaseDriverFactory().create()
            ),
            imageStorage = ImageStorage()
        )
    }
}