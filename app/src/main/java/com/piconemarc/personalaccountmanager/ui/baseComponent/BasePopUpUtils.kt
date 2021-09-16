package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge

@Composable
fun PopUpTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary, shape = MaterialTheme.shapes.large)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = RegularMarge),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun popUpTextFieldColorSelector(
    isAmount: Boolean = false,
    amount: Double = 0.00
): Color {
    return when (isAmount) {
        false -> MaterialTheme.colors.primary
        true -> if (amount <= 0) {
            Color.Red
        } else Color.Green
    }
}

val testList = mutableListOf("test 1", "test 2", "test 3")