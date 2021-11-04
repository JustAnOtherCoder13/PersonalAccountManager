package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.personalaccountmanager.*
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.base.BaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.deleteAccountPopUpHeight
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.deleteAccountUiState

@Composable
fun DeleteAccountPopUp(
    viewModel: AppViewModel
) {
    BaseDeletePopUp(
        deletePopUpTitle = stringResource(R.string.deleteAccountPopUpTitle),
        onAcceptButtonClicked = { viewModel.dispatchAction(
            AppActions.DeleteAccountAction.DeleteAccount(deleteAccountUiState.accountToDelete)
        ) },
        onDismiss = { viewModel.dispatchAction(
            AppActions.DeleteAccountAction.ClosePopUp
        ) },
        isExpanded = deleteAccountUiState.isPopUpExpanded
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(deleteAccountPopUpHeight)
        ) {
            Text(
                text = deleteAccountUiState.accountToDelete.name,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(vertical = LittleMarge)
            )
            Text(
                text = "${deleteAccountUiState.accountToDelete.accountBalance.toStringWithTwoDec()} ${getCurrencySymbolForLocale(currentLocale)}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = LittleMarge),
            )
        }
    }
}