package com.piconemarc.personalaccountmanager.ui.component.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PaymentPostItBody
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PaymentPostItFooter
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PaymentPostItTitle
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.PostItBackground
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.VerticalDispositionSheet
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.paymentPostItInitialHeight
import com.piconemarc.personalaccountmanager.ui.theme.paymentPostItItemHeight
import com.piconemarc.personalaccountmanager.ui.theme.paymentPostItWidth
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
                { accountWithRelatedPayments ->
                    Box(
                        modifier = Modifier
                            .width(paymentPostItWidth)
                            .height(
                                paymentPostItInitialHeight
                                        + (accountWithRelatedPayments.relatedPayment.count()
                                        * paymentPostItItemHeight).dp
                            )
                            .padding(vertical = RegularMarge)
                    ) {
                        PostItBackground(this)
                        VerticalDispositionSheet(
                            header = {
                                PaymentPostItTitle(
                                    onAddPaymentButtonClick = {
                                        viewModel.dispatchAction(
                                            AppActions.AddOperationPopUpAction.InitPopUp(
                                                isOnPaymentScreen = true,
                                                selectedAccountId = accountWithRelatedPayments.account.id
                                            )
                                        )
                                    },
                                    accountName = accountWithRelatedPayments.account.name
                                )
                            },
                            body = {
                                PaymentPostItBody(
                                    accountWithRelatedPayments = accountWithRelatedPayments,
                                    onDeletePaymentButtonClick = { paymentToDelete ->
                                        viewModel.dispatchAction(
                                            AppActions.DeleteOperationPopUpAction.InitPopUp(
                                                paymentToDelete
                                            )
                                        )
                                    }
                                )
                            },
                            footer = { PaymentPostItFooter(accountWithRelatedPayments) }
                        )
                    }
                }
            }
        }
    )
}
