package com.plcoding.contactscomposemultiplatform.di

import android.content.Context
import com.plcoding.contactscomposemultiplatform.contacts.data.SqlDelightContactDataSource
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.core.data.DatabaseDriverFactory
import com.plcoding.contactscomposemultiplatform.core.data.ImageStorage
import com.plcoding.contactsmultiplatform.database.ContactDatabase

actual class AppModule(private val context: Context) {
    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            db = ContactDatabase(
                DatabaseDriverFactory(
                    context
                ).create()
            ),
            imageStorage = ImageStorage(context)
        )
    }
}