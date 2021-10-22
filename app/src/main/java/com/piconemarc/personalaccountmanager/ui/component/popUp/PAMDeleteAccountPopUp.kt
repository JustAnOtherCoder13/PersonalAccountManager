package com.piconemarc.personalaccountmanager.ui.component.popUp

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.piconemarc.model.entity.PresentationDataModel
import com.piconemarc.personalaccountmanager.ui.component.pieceOfComponent.PAMBaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.viewmodel.viewModel.AppActionDispatcher
import com.piconemarc.viewmodel.viewModel.AppActions
import com.piconemarc.viewmodel.viewModel.AppSubscriber.GlobalUiState.deleteAccountUiState

@Composable
fun PAMDeleteAccountPopUp(
    actionDispatcher: AppActionDispatcher
) {

    PAMBaseDeletePopUp(
        deletePopUpTitle = PresentationDataModel("Delete Account"),
        onAcceptButtonClicked = { actionDispatcher.dispatchAction(
            AppActions.DeleteAccountAction.DeleteAccount(deleteAccountUiState.accountToDeleteName.objectIdReference)
        ) },
        onDismiss = { actionDispatcher.dispatchAction(
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
                text = deleteAccountUiState.accountToDeleteName.stringValue,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(vertical = LittleMarge)
            )
            Text(
                text = deleteAccountUiState.accountToDeleteBalance.stringValue+" €",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = LittleMarge)
            )
        }

    }

}