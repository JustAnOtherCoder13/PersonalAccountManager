package com.piconemarc.personalaccountmanager

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.piconemarc.personalaccountmanager.ui.theme.NegativeText
import com.piconemarc.personalaccountmanager.ui.theme.PositiveText
import java.util.*

fun Double.toStringWithTwoDec() = String.format("%.2f",this)

@Composable
fun getBlackOrNegativeColor(amount : Double): androidx.compose.ui.graphics.Color{
    return if (amount >=0) MaterialTheme.colors.onSecondary else NegativeText
}

fun getPositiveOrNegativeColor(amount : Double): androidx.compose.ui.graphics.Color{
    return if (amount >=0) PositiveText else NegativeText
}

fun getCurrencySymbolForLocale(locale: Locale) : String{
    return Currency.getInstance(locale).symbol
}

fun getAddOrMinusFormattedValue(amount : Double) : String{
    val str = "${amount.toStringWithTwoDec()} ${getCurrencySymbolForLocale(currentLocale)}"
    return if (amount<0) str else "+$str"
}

var currentLocale: Locale = Locale.getDefault()