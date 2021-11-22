package com.piconemarc.personalaccountmanager.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.piconemarc.model.entity.PaymentUiModel
import com.piconemarc.model.getDateMonth
import com.piconemarc.model.getDateYear
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PaymentPostItBody
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PaymentPostItFooter
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PaymentPostItTitle
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.PostItBackground
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.VerticalDispositionSheet
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.paymentPostItInitialHeight
import com.piconemarc.personalaccountmanager.ui.theme.paymentPostItItemHeight
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates
import java.util.*

@Composable
fun MyPaymentScreen(
    paymentState: ViewModelInnerStates.PaymentScreenVmState,
    onAddPaymentButtonClick: (AppActions.AddOperationPopupAction) -> Unit,
    onDeletePaymentButtonClick: (AppActions.DeleteOperationPopUpAction) -> Unit,
    onPaymentEvent: (AppActions.PaymentScreenAction) -> Unit
) {
    if (paymentState.passPaymentToastMessage.trim().isNotEmpty())
        Toast.makeText(
            LocalContext.current,
            paymentState.passPaymentToastMessage,
            Toast.LENGTH_SHORT
        ).show()
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
                            .padding(horizontal = RegularMarge)
                            .fillMaxWidth()
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
                                    accountWithRelatedPayments = accountWithRelatedPayments,
                                    areAllPaymentForAccountPassedThisMonth = arePaymentPassedThisMonth(
                                        accountWithRelatedPayments.relatedPayment.map {
                                            paymentIsPassedAndHaveNoEndDate(it) || paymentIsPassedAndEndDateIsNotPast(it)
                                        }),
                                    onPassAllPaymentButtonClick = onPaymentEvent
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
                                    },
                                    onPassPaymentButtonClick = onPaymentEvent
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

@Composable
private fun paymentIsPassedAndEndDateIsNotPast(it: PaymentUiModel) =
    it.isPaymentPassForThisMonth &&
            (it.endDate?.getDateMonth()!! < Calendar.getInstance().time.getDateMonth()
                    || it.endDate?.getDateYear()!! <= Calendar.getInstance().time.getDateYear())

@Composable
private fun paymentIsPassedAndHaveNoEndDate(it: PaymentUiModel) =
    it.isPaymentPassForThisMonth && it.endDate == null

fun arePaymentPassedThisMonth(arePaymentPassed: List<Boolean>): Boolean =
    if (arePaymentPassed.isEmpty()) true
    else arePaymentPassed.contains(true)

