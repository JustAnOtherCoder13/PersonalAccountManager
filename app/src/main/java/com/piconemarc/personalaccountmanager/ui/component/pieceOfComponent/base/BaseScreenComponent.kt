package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.ui.theme.*

@SuppressLint("ModifierParameter")
@Composable
fun VerticalDispositionSheet(
    header: @Composable () -> Unit = {},
    body: @Composable () -> Unit,
    footer: @Composable () -> Unit = {},
    sheetModifier: Modifier = Modifier.fillMaxSize(),
    headerAndBodyColumnModifier: Modifier = Modifier.fillMaxSize(),
    headerModifier: Modifier = Modifier.fillMaxWidth(),
    bodyModifier: Modifier = Modifier.fillMaxWidth(),
    footerModifier: Modifier = Modifier.wrapContentWidth(),
    columnHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    columnVerticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.SpaceBetween,
) {
    Column(
        modifier = sheetModifier,
        horizontalAlignment = columnHorizontalAlignment,
        verticalArrangement = columnVerticalArrangement
    ) {
        Column(
            modifier = headerAndBodyColumnModifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row(modifier = headerModifier) {
                header()
            }
            Row(modifier = bodyModifier) {
                body()
            }
        }
        Row(
            modifier = footerModifier,
        ) {
            footer()
        }
    }
}

@Composable
fun BaseScreen(
    header: @Composable () -> Unit,
    body: @Composable () -> Unit,
    footer: @Composable () -> Unit
) {
    VerticalDispositionSheet(
        header = header,
        body = body,
        footer = footer,
        bodyModifier = Modifier.padding(vertical = RegularMarge)
    )
}


@Composable
fun PostItTitle(
    accountName: String,
    onAccountButtonClicked: () -> Unit,
    iconButton : PAMIconButtons,
    onPassAllPaymentForAccountClick : ()-> Unit = {},
    areAllPaymentsForAccountPassedThisMonth : Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LittleMarge),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = accountName,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.weight(0.7f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
            )
        if (!areAllPaymentsForAccountPassedThisMonth)
        Box(modifier = Modifier.height(35.dp).weight(0.15f), contentAlignment = Alignment.CenterEnd){
            BaseIconButton(
                onIconButtonClicked = {
                    onPassAllPaymentForAccountClick()
                },
                iconButton = PAMIconButtons.UpdatePayment,
                iconColor = NegativeText,
                backgroundColor = Color.Transparent,
            )
        }

        IconButton(
            onClick = onAccountButtonClicked,
            modifier = Modifier.weight(0.15f)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconButton.vectorIcon),
                contentDescription =
                stringResource(
                    id = iconButton.iconContentDescription
                )
            )
        }
    }
    Divider(
        modifier = Modifier
            .padding(start = LittleMarge, end = LittleMarge, bottom = RegularMarge),
        color = MaterialTheme.colors.onSecondary,
        thickness = ThinBorder
    )
}

@Composable
fun PostItBackground(boxScope: BoxScope) {
    with(boxScope) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = PastelYellow,
                    shape = CutCornerShape(bottomEnd = BigMarge)
                )
                .border(
                    width = ThinBorder,
                    color = BrownDark_300,
                    shape = CutCornerShape(bottomEnd = BigMarge)
                )
        )
        Box(
            modifier = Modifier
                .size(width = BigMarge, height = BigMarge)
                .align(Alignment.BottomEnd)
                .background(
                    color = PastelYellowLight,
                    shape = CutCornerShape(bottomEnd = BigMarge)
                )
                .border(
                    width = ThinBorder,
                    color = BrownDark_300,
                    shape = CutCornerShape(bottomEnd = BigMarge)
                ),
        )
    }
}