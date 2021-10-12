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
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationScreenEvent
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationScreenViewState

@SuppressLint("ModifierParameter")
@Composable
fun PAMAddOperationPopUp() {

    //Pop up Body --------------------------------------------
    PAMBasePopUp(
        title = "",
        onAcceptButtonClicked = { },
        onDismiss = { AddOperationScreenEvent.closePopUp() },
        isExpanded = AddOperationScreenViewState.isPopUpExpanded,
        menuIconPanel = {
            PAMAddOperationPopUpLeftSideMenuIconPanel(
                isTransferOptionExpanded = AddOperationScreenViewState.isTransferExpanded,
                isPaymentOptionExpanded = AddOperationScreenViewState.isPaymentExpanded
            )
        }
    ) {
        //category drop down -----------------------------------
        PAMBaseDropDownMenuWithBackground(
            selectedItem = AddOperationScreenViewState.selectedCategoryName,
            itemList = AddOperationScreenViewState.operationCategories,
            onItemSelected = {category -> AddOperationScreenEvent.selectCategory(category) }
        )
        // operation and amount text field--------------------------
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PAMBlackBackgroundTextFieldItem(
                title = stringResource(R.string.operationName),
                onTextChange = {operationName-> AddOperationScreenEvent.fillOperationName(operationName) },
                textValue = AddOperationScreenViewState.operationName,
            )
            PAMAmountTextFieldItem(
                title = stringResource(R.string.operationAmount),
                onTextChange = { },
                amountValue = "",
            )
            //Payment Operation option--------------------------
            PAMPunctualOrRecurrentSwitchButton(
                isRecurrentOptionExpanded = AddOperationScreenViewState.isRecurrentOptionExpanded,
                isPaymentOptionExpanded = AddOperationScreenViewState.isPaymentExpanded
            )
            //Transfer Operation option---------------------------
            PAMTransferOptionPanel(isTransferOptionExpanded = AddOperationScreenViewState.isTransferExpanded)
        }
    }
}