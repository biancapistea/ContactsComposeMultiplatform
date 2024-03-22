package com.plcoding.contactscomposemultiplatform.core.data

import android.content.Context
import com.plcoding.contactsmultiplatform.database.ContactDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(ContactDatabase.Schema, context, "contact.db")
    }
}