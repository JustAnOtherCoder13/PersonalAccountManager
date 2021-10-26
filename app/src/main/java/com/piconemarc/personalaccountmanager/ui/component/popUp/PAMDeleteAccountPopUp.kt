package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppViewModel
import com.piconemarc.viewmodel.viewModel.reducer.AppSubscriber.AppUiState.deleteAccountUiState

@Composable
fun PAMDeleteAccountPopUp(
    viewModel: AppViewModel
) {

    PAMBaseDeletePopUp(
        deletePopUpTitle = "Delete Account",
        onAcceptButtonClicked = { viewModel.dispatchAction(
            AppActions.DeleteAccountAction.DeleteAccount(deleteAccountUiState.accountUiToDelete)
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
                .height(100.dp)
        ) {
            Text(
                text = deleteAccountUiState.accountUiToDelete.name,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(vertical = LittleMarge)
            )
            Text(
                text = deleteAccountUiState.accountUiToDelete.accountBalance.toString() +" €",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = LittleMarge)
            )
        }

    }

}