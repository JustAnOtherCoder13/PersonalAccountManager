package com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.model.entity.AccountWithRelatedPaymentUiModel
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.personalaccountmanager.*
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseDeleteIconButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.PostItTitle
import com.piconemarc.personalaccountmanager.ui.theme.*


@Composable
fun PaymentPostItBody(
    accountWithRelatedPayments: AccountWithRelatedPaymentUiModel,
    onDeletePaymentButtonClick: (paymentTODelete: PaymentUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        accountWithRelatedPayments.relatedPayment.forEachIndexed { index, relatedPayment ->
            RelatedPaymentItem(
                index = index,
                relatedPayment = relatedPayment,
                onDeletePaymentButtonClick = { paymentToDelete ->
                    onDeletePaymentButtonClick(paymentToDelete)
                }
            )
        }
    }
}

@Composable
fun PaymentPostItFooter(accountWithRelatedPayments: AccountWithRelatedPaymentUiModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            color = MaterialTheme.colors.onSecondary,
            thickness = ThinBorder,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LittleMarge)
        )
        var totalAmount = 0.0
        accountWithRelatedPayments.relatedPayment.map { payment -> payment.amount }
            .forEach { paymentAmount -> totalAmount += paymentAmount }
        Row(modifier = Modifier.padding(start = LittleMarge, top = LittleMarge,bottom = LittleMarge)) {
            Text(
                text = stringResource(R.string.paymentPostItTotal),
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = " ${totalAmount.toStringWithTwoDec()} ${getCurrencySymbolForLocale(currentLocale)}",
                style = MaterialTheme.typography.body1,
                color = getBlackOrNegativeColor(amount = totalAmount)
            )
        }

    }
}

@Composable
fun RelatedPaymentItem(
    index: Int,
    relatedPayment: PaymentUiModel,
    onDeletePaymentButtonClick: (paymentTODelete: PaymentUiModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LittleMarge)
            .background(color = if (index % 2 == 0) PastelYellowLight else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = relatedPayment.name,
            modifier = Modifier.weight(1.5f),
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text =" ${relatedPayment.amount.toStringWithTwoDec()} ${getCurrencySymbolForLocale(currentLocale)}",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body1,
            color = getPositiveOrNegativeColor(relatedPayment.amount)
        )
        BaseDeleteIconButton(
            onDeleteItemButtonCLick = { paymentToDelete ->
                onDeletePaymentButtonClick(
                    paymentToDelete
                )
            },
            uiModel = relatedPayment
        )
    }
}

@Composable
fun PaymentPostItTitle(
    onAddPaymentButtonClick: () -> Unit,
    accountName: String
) {
    Column {
        PostItTitle(
            account = accountName,
            onAccountButtonClicked = { onAddPaymentButtonClick() },
            iconButton = PAMIconButtons.Add
        )
    }
}