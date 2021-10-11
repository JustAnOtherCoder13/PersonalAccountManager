package com.piconemarc.personalaccountmanager.ui.component.popUp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.*
import com.piconemarc.viewmodel.viewModel.AddOperationPopUpEvent
import com.piconemarc.viewmodel.viewModel.AddOperationPopUpState
import com.piconemarc.viewmodel.viewModel.AccountDetailViewModel

@SuppressLint("ModifierParameter")
@Composable
fun PAMAddOperationPopUp(
    accountDetailViewModel: AccountDetailViewModel
) {

    //Pop up Body --------------------------------------------
    PAMBasePopUp(
        title = "",
        onAcceptButtonClicked = { },
        onDismiss = { accountDetailViewModel.dispatchEvent(AddOperationPopUpEvent.CLosePopUp) },
        isExpanded = AddOperationPopUpState.isPopUpExpanded,
        menuIconPanel = {
            PAMAddOperationPopUpLeftSideMenuIconPanel(
                isTransferOptionExpanded = AddOperationPopUpState.isTransferExpanded,
                isPaymentOptionExpanded = AddOperationPopUpState.isPaymentExpanded,
                operationDetailViewModel = accountDetailViewModel
            )
        }
    ) {
        //category drop down -----------------------------------
        PAMBaseDropDownMenuWithBackground(
            selectedItem = AddOperationPopUpState.selectedCategoryName,
            itemList = AddOperationPopUpState.operationCategories,
            onItemSelected = { category ->
                accountDetailViewModel.dispatchEvent(
                    AddOperationPopUpEvent.SelectCategory(category)
                )
            }
        )
        // operation and amount text field--------------------------
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PAMBlackBackgroundTextFieldItem(
                title = stringResource(R.string.operationName),
                onTextChange = { operationName ->
                    accountDetailViewModel.dispatchEvent(
                        AddOperationPopUpEvent.FillOperationName(operationName)
                    )
                },
                textValue = AddOperationPopUpState.operationName,
            )
            PAMAmountTextFieldItem(
                title = stringResource(R.string.operationAmount),
                onTextChange = {},
                amountValue = "",
            )
            //Payment Operation option--------------------------
            PAMPunctualOrRecurrentSwitchButton(
                isRecurrentOptionExpanded = AddOperationPopUpState.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = AddOperationPopUpState.isPaymentExpanded,
                operationDetailViewModel = accountDetailViewModel
            )
            //Transfer Operation option---------------------------
            PAMTransferOptionPanel(accountDetailViewModel = accountDetailViewModel)
        }
    }
}