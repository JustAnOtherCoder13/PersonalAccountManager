package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.piconemarc.model.PAMIconButtons
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.AccountPostItBackground
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PostItTitle
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.RowDeleteIconButton
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.VerticalDispositionSheet
import com.piconemarc.personalaccountmanager.ui.theme.*
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.paymentScreenUiState

@Composable
fun PaymentScreen(viewModel: AppViewModel) {
    VerticalDispositionSheet(
        body = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = RegularMarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(paymentScreenUiState.allAccounts)
                {
                    Box(
                        modifier = Modifier
                            .width(250.dp)
                            .height(100.dp +(it.relatedPayment.count() * 36).dp)
                            .padding(vertical = RegularMarge)
                    ) {
                        AccountPostItBackground(this)
                        VerticalDispositionSheet(
                            header = {
                                Column {
                                    PostItTitle(
                                        account = it.accountUiModel.name,
                                        onAccountButtonClicked = {
                                            viewModel.dispatchAction(
                                                AppActions.AddOperationPopUpAction.InitPopUp(true, selectedAccountId = it.accountUiModel.id )
                                            )
                                        },
                                        iconButton = PAMIconButtons.Add
                                    )
                                }
                            },
                            body = {
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()) {
                                    it.relatedPayment.forEachIndexed { index, payment ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = LittleMarge)
                                                .background(color = if (index % 2 == 0) PastelYellowLight else Color.Transparent),
                                            verticalAlignment = CenterVertically
                                        ) {
                                            Text(
                                                text = payment.name,
                                                modifier = Modifier.weight(1.5f),
                                                style = MaterialTheme.typography.body1
                                            )
                                            Text(
                                                text = payment.amount.toString(),
                                                modifier = Modifier.weight(1f),
                                                style = MaterialTheme.typography.body1,
                                                color = if (payment.amount < 0) NegativeText else PositiveText
                                            )
                                            RowDeleteIconButton(
                                                onDeleteItemButtonCLick = {
                                                    viewModel.dispatchAction(
                                                        AppActions.DeleteOperationPopUpAction.InitPopUp(it)
                                                    )
                                                },
                                                uiModel = payment
                                            )
                                        }
                                    }
                                }
                            },
                            footer = {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Divider(
                                        color = MaterialTheme.colors.onSecondary,
                                        thickness = ThinBorder,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = LittleMarge)
                                    )
                                    var totalAmount  = 0.0
                                    it.relatedPayment.map { it.amount }.forEach { totalAmount+=it }
                                    Text(
                                        text = "Total = $totalAmount",
                                        style = MaterialTheme.typography.body1
                                        )
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}