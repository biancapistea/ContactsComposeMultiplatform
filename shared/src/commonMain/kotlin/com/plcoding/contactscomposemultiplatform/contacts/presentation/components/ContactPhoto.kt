package com.plcoding.contactscomposemultiplatform.contacts.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plcoding.contactscomposemultiplatform.contacts.domain.Contact
import com.plcoding.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.plcoding.contactscomposemultiplatform.core.presentation.rememberBitmapFromBytes

@Composable
fun ContactPhoto(modifier: Modifier = Modifier, contact: Contact?, iconSize: Dp = 25.dp) {
    val bitmap = rememberBitmapFromBytes(contact?.photoBytes)
    val photoModifier = modifier.clip(RoundedCornerShape(35))
    if (bitmap != null) {
        Image(
            modifier = photoModifier,
            bitmap = bitmap,
            contentDescription = contact?.firstName,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = photoModifier.background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = Icons.Rounded.Person,
                contentDescription = contact?.firstName,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
