package com.piconemarc.personalaccountmanager.ui.component.popUp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.*
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpScreenModel
import com.piconemarc.viewmodel.viewModel.test.AddOperationPopUpAction
import com.piconemarc.viewmodel.viewModel.test.DI

@SuppressLint("ModifierParameter")
@Composable
fun PAMAddOperationPopUp(screenModel: AddOperationPopUpScreenModel) {
    var isPopUpExpanded  by remember {
        mutableStateOf(false)
    }
    var isRecurrentOptionExpanded by remember {
        mutableStateOf(false)
    }
    var isPaymentOptionExpanded by remember {
        mutableStateOf(false)
    }
    var isTransferOptionExpanded by remember {
        mutableStateOf(false)
    }
    DI.store.add {
        isPopUpExpanded = it.addOperationPopUpOperationOptionState.isPopUpExpanded
        isRecurrentOptionExpanded = it.addOperationPopUpPaymentOptionState.isRecurrentOptionExpanded
        isPaymentOptionExpanded = it.addOperationPopUpPaymentOptionState.isPaymentExpanded
        isTransferOptionExpanded = it.addOperationPopUpTransferOptionState.isTransferExpanded
    }

    //Pop up Body --------------------------------------------
    PAMBasePopUp(
        title = screenModel.getState().popUpTitle,
        onAcceptButtonClicked = { screenModel.addOperation() },
        onDismiss = { DI.store.dispatch(AddOperationPopUpAction.ClosePopUp) },
        isExpanded = isPopUpExpanded ,
        menuIconPanel = { PAMAddOperationPopUpLeftSideMenuIconPanel(isTransferOptionExpanded = isTransferOptionExpanded, isPaymentOptionExpanded = isPaymentOptionExpanded) }
    ) {
        //category drop down -----------------------------------
        PAMBaseDropDownMenuWithBackground(
            selectedItem = screenModel.getState().category.name,
            itemList = screenModel.getState().allCategories,
            onItemSelected = { screenModel.selectCategory(it) }
        )
        // operation and amount text field--------------------------
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PAMBlackBackgroundTextFieldItem(
                title = stringResource(R.string.operationName),
                onTextChange = { screenModel.enterOperationName(it) },
                textValue = screenModel.getState().operationName,
            )
            PAMAmountTextFieldItem(
                title = stringResource(R.string.operationAmount),
                onTextChange = { screenModel.enterAmountValue(it) },
                amountValue = screenModel.getState().operationAmount,
                screenModel = screenModel
            )
            //Payment Operation option--------------------------
            PAMPunctualOrRecurrentSwitchButton(isRecurrentOptionExpanded = isRecurrentOptionExpanded, isPaymentOptionExpanded = isPaymentOptionExpanded )
            //Transfer Operation option---------------------------
            PAMTransferOptionPanel(isTransferOptionExpanded = isTransferOptionExpanded)
        }
    }
}