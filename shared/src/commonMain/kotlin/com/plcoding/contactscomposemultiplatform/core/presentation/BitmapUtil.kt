package com.plcoding.contactscomposemultiplatform.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap?
//expect means that we want to have this function but the actual implementation is in androidMain and in iosMain