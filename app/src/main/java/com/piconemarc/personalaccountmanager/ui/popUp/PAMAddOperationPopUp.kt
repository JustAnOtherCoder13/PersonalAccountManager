package com.piconemarc.personalaccountmanager.ui.popUp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.component.*
import com.piconemarc.viewmodel.viewModel.addOperationPopUp.AddOperationPopUpScreenModel

@SuppressLint("ModifierParameter")
@Composable
fun PAMAddOperationPopUp(screenModel : AddOperationPopUpScreenModel) {

    //Pop up Body --------------------------------------------
    LazyColumn {
        item {
            PAMBasePopUp(
                title = screenModel.getState().popUpTitle,
                onAcceptButtonClicked = { screenModel.addOperation() },
                onDismiss = { screenModel.closeAddPopUp() },
                isExpanded = screenModel.getState().isExpanded,
                menuIconPanel = { PAMAddOperationPopUpLeftSideMenuIconPanel(screenModel) }
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
                        onTextChange = {screenModel.enterAmountValue(it)},
                        amountValue = screenModel.getState().operationAmount,
                        screenModel = screenModel
                    )
                    //Payment Operation option--------------------------
                    PAMPunctualOrRecurrentSwitchButton(
                        screenModel = screenModel
                    )
                    //Transfer Operation option---------------------------
                    PAMTransferOptionPanel(screenModel = screenModel)
                }
            }

        }
    }
}