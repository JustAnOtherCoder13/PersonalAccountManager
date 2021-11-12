package com.piconemarc.personalaccountmanager.ui.screen

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
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

@Composable
fun MyPaymentScreen(
    paymentState : ViewModelInnerStates.PaymentScreenVmState,
    onAddPaymentButtonClick : (AppActions.AddOperationPopupAction)->Unit,
    onDeletePaymentButtonClick : (AppActions.DeleteOperationPopUpAction)-> Unit

) {
    VerticalDispositionSheet(
        body = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = RegularMarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(paymentState.allAccounts)
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
                                        onAddPaymentButtonClick(
                                            AppActions.AddOperationPopupAction.InitPopUp(
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
                                        onDeletePaymentButtonClick(
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