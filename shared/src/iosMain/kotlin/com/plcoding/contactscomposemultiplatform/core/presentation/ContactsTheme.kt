package com.plcoding.contactscomposemultiplatform.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.plcoding.contactscomposemultiplatform.ui.theme.DarkColorScheme
import com.plcoding.contactscomposemultiplatform.ui.theme.LightColorScheme
import com.plcoding.contactscomposemultiplatform.ui.theme.Typography

@Composable
actual fun ContactsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}