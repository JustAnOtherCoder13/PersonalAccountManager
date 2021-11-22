package com.piconemarc.personalaccountmanager.ui.popUp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.theme.NegativeText
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder
import com.piconemarc.viewmodel.viewModel.utils.AppActions
import com.piconemarc.viewmodel.viewModel.utils.ViewModelInnerStates

@Composable
fun DeleteObsoletePaymentPopUp(
    deleteObsoletePaymentPopUpState: ViewModelInnerStates.DeleteObsoletePaymentPopUpVMState,
    onDeleteObsoletePaymentEvent: (AppActions.DeleteObsoletePaymentPopUpAction) -> Unit
) {
    BaseDeletePopUp(
        deletePopUpTitle = stringResource(R.string.deleteObsoletePaymentPopUpTitle),
        onAcceptButtonClicked = {
            onDeleteObsoletePaymentEvent(
                AppActions.DeleteObsoletePaymentPopUpAction.DeleteObsoletePayment(
                    deleteObsoletePaymentPopUpState.obsoletePaymentToDelete
                )
            )
        },
        onDismiss = {
                    //don't dismiss to force deletion of obsolete payment
        },
        isExpanded = deleteObsoletePaymentPopUpState.isVisible,
        isDismissButtonVisible = false
    ) {

        Column(modifier = Modifier.padding(vertical = RegularMarge)) {
            Text(
                text = stringResource(R.string.deleteObsoletePaymentFinishMessage),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(RegularMarge),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(RegularMarge)
                    .border(
                        width = ThinBorder,
                        color = MaterialTheme.colors.onSecondary,
                        shape = RoundedCornerShape(
                            RegularMarge
                        )
                    )
            ) {
                deleteObsoletePaymentPopUpState.obsoletePaymentToDelete.forEach {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = RegularMarge),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            Column(modifier = Modifier.padding(vertical = RegularMarge)) {
                Text(
                    text = if (deleteObsoletePaymentPopUpState.obsoletePaymentToDelete.size == 1)
                        stringResource(R.string.deleteObsoletePaymentSinglePaymentDeletedMessage)
                    else stringResource(R.string.deleteObsoletePaymentMultiplePaymentDeletedMessage),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(RegularMarge),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = NegativeText
                )
            }
        }
    }
}